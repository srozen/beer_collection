package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by Rémy on 20-10-15.
 * Classe Abstraite pour une Activité qui utiliserais un ServeurCom ()
 */
public abstract class ActivityCom extends Activity {

    protected Boolean chargementFini = true;

    public abstract void communication(JSONObject rep);

    protected void connexionValide(String username, String password){
        SharedPreferences log = getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor edit = log.edit();
        edit.putString("username", username);
        edit.putString("password", password);
        edit.apply();
        Intent i2= new Intent(this,Principal.class);
        startActivity(i2);
    }
}
