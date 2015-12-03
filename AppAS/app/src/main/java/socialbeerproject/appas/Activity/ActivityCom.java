package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import socialbeerproject.appas.R;

/**
 * Created by Rémy on 20-10-15.
 * Classe Abstraite pour une Activité qui utiliserais un ServeurCom ()
 */
public abstract class ActivityCom extends Activity {

    protected Boolean chargementFini = true;
    private String hash;
    private String idUser;

    public abstract void communication(JSONObject rep);

    protected void connexionValide(String username){
        SharedPreferences log = getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor edit = log.edit();
        edit.putString("username", username);
        edit.putString("hash", this.hash);
        edit.putString("idUser", this.idUser);
        edit.apply();

        SharedPreferences prefs = getSharedPreferences("SIP", MODE_PRIVATE);
        SharedPreferences.Editor editSip = prefs.edit();
        editSip.putString("username", Integer.toString(6000 + (Integer.parseInt(this.idUser)) ));
        editSip.putString("password", this.idUser);
        editSip.putString("idUser", this.idUser);
        editSip.apply();

        Intent i2= new Intent(this,Principal.class);
        startActivity(i2);
    }

    public void messageErreur(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle).create();
        alertDialog.setTitle("Erreur !");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void printToast(String mess){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.linear_toast_layout));
        Toast toast = new Toast(this);
        TextView txt = (TextView) layout.findViewById(R.id.textView_toast);
        txt.setText(mess);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void startActivity(Intent i){
        super.startActivity(i);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setHash(String hash){
        this.hash = hash;
    }

    public void setIdUser(String idUser){
        this.idUser = idUser;
    }
}
