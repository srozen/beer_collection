package socialbeerproject.appas.Activity;

import android.app.Activity;

import org.json.JSONObject;

/**
 * Created by Rémy on 20-10-15.
 * Classe Abstraite pour une Activité qui utiliserais un ServeurCom ()
 */
public abstract class ActivityCom extends Activity {
    protected void enregistrementLog(String username, String password){

    }

    protected String getUsername(){
        return null;
    }

    protected String getMdp(){
        return null;
    }

    public abstract void communication(JSONObject rep);
}
