package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONObject;

import socialbeerproject.appas.R;

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

    @Override
    public void startActivity(Intent i){
        super.startActivity(i);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
