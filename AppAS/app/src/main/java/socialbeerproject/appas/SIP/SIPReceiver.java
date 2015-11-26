package socialbeerproject.appas.SIP;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.*;

import socialbeerproject.appas.Activity.PhoneA;


/**
 * Ecoute si l'application reçoit un appel et l'envoie à Phone.
 */
public class SIPReceiver extends BroadcastReceiver {

    public boolean isConn =false;

    public SIPReceiver(){
        super();
        isConn=true;
    }

    /**
     * Processes the incoming call, answers it, and hands it over to the
     * PhoneActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SipAudioCall incomingCall = null;
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            PhoneA phoneAct = (PhoneA) context;

            incomingCall = phoneAct.manager.takeAudioCall(intent, listener);
            incomingCall.answerCall(5);
            incomingCall.startAudio();
            incomingCall.setSpeakerMode(true);
            if(incomingCall.isMuted()) {
                incomingCall.toggleMute();
            }

            phoneAct.call = incomingCall;

        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }
}
