package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;

import socialbeerproject.appas.R;
import socialbeerproject.appas.SIP.SIPReceiver;


/**
 * Handles all calling, receiving calls, and UI interaction in the WalkieTalkie app.
 */
public class PhoneA extends Activity implements View.OnClickListener {
    public String sipAddress = "sip:6002@46.101.143.168";
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public SIPReceiver callReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Bundle b = getIntent().getExtras();
        String idAmi = b.getString("id");
        sipAddress = "sip:"+ Integer.toString(6000 + Integer.parseInt(idAmi)) +"@46.101.143.168";

        // Set up the intent filter.  This will be used to fire an
        // IncomingCallReceiver when someone calls the SIP address used by this
        // application.
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new SIPReceiver();
        this.registerReceiver(callReceiver, filter);
        // "Push to talk" can be a serious pain when the screen keeps turning off.
        // Let's prevent that.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initializeManager();

        Button btnAInsc = (Button) findViewById(R.id.sip_button);
        btnAInsc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sip_button:
                initiateCall();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        initializeManager();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.close();
        }
        closeLocalProfile();
        if (callReceiver != null) {
            this.unregisterReceiver(callReceiver);
        }
    }
    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(this);
        }
        initializeLocalProfile();
    }
    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    public void initializeLocalProfile() {
        if (manager == null) {
            return;
        }
        if (me != null) {
            closeLocalProfile();
        }

        SharedPreferences prefs = this.getSharedPreferences("SIP", Context.MODE_PRIVATE);
        String username = prefs.getString("username","");
        String domain = "46.101.143.168";
        String password = prefs.getString("idUser","");

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            me = builder.build();

            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);
            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.
            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Registering with SIP Server...");
                }
                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }
                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed.  Please check settings.");
                }
            });
        } catch (ParseException pe) {
            updateStatus("Connection Error.");
        } catch (SipException se) {
            updateStatus("Connection error.");
        }
    }
    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    public void closeLocalProfile() {
        if (manager == null) {
            return;
        }
        try {
            if (me != null) {
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }
    /**
     * Make an outgoing call.
     */
    public void initiateCall() {
        updateStatus(sipAddress);
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    System.out.println("SALUT");
                    call.setSpeakerMode(true);
                    if (call.isMuted()){
                        call.toggleMute();
                    }
                    updateStatus(call);
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Ready.");
                }
            };
            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);
        }
        catch (Exception e) {
            Log.i("WalkieTalkieActivity/InitiateCall", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    Log.i("WalkieTalkieActivity/InitiateCall",
                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
        }
    }
    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                TextView labelView = (TextView) findViewById(R.id.sip_label);
                labelView.setText(status);
            }
        });
    }
    /**w
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }
}