package socialbeerproject.appas.SIP;

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
import android.view.WindowManager;

import java.text.ParseException;

/**
 * Handles all calling, receiving calls, and UI interaction in the WalkieTalkie app.
 */
public class Phone {
    public String sipAddress = "sip:6002@46.101.143.168";
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public SIPReceiver callReceiver;
    public Activity activity;

    public String status;

    /** L'instance statique */
    private static Phone instance;

    /** Récupère l'instance unique de la class Singleton.
     * Remarque : le constructeur est rendu inaccessible
     */
    public static Phone getInstance() {
        if (null == instance) { // Premier appel
            instance = new Phone();
        }
        return instance;
    }

    private Phone(){

    }

    public void close() {
        if (call != null) {
            call.close();
        }
        closeLocalProfile();
        if (callReceiver != null) {
            activity.unregisterReceiver(callReceiver);
        }
    }

    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(activity);
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

        SharedPreferences prefs = activity.getSharedPreferences("SIP", Context.MODE_PRIVATE);
        String username = prefs.getString("username","6000");
        String domain = "46.101.143.168";
        String password = prefs.getString("idUser","0");

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            me = builder.build();

            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(activity, 0, i, Intent.FILL_IN_DATA);

            manager.open(me, pi, null);
            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.
            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Registering");

                }
                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }
                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed");
                }
            });
        } catch (ParseException pe) {
            updateStatus("Connection Error");
        } catch (SipException se) {
            updateStatus("Connection error");
        }
    }

    /**
     * Ferme le profil Local
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
        }
    }

    /**
     * Make an outgoing call.
     */
    public void initiateCall(String idAmi) {
        sipAddress = "sip:"+ Integer.toString(6000 + Integer.parseInt(idAmi)) +"@46.101.143.168";
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    if (call.isMuted()){
                        call.toggleMute();
                    }
                    updateStatus("Connect");
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Ready");
                }
            };
            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);
        }
        catch (Exception e) {
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
        }
    }

    public void attach(Activity act){
        this.activity = act;

        setPendingIntent(act);

        act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeManager();
    }

    public void setPendingIntent(Activity act){
        // Set up the intent filter.  This will be used to fire an
        // IncomingCallReceiver when someone calls the SIP address used by this
        // application.
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new SIPReceiver();
        activity.registerReceiver(callReceiver, filter);
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(String status) {
        this.status = status;
    }
}