package socialbeerproject.appas.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Login extends ActivityCom implements View.OnClickListener{

    private Button btnAInsc = null;
    private Button btnSeConnecter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.premiereConnexion();

        btnAInsc = (Button) findViewById(R.id.btn_A_Inscription);
        btnAInsc.setOnClickListener(this);
        btnSeConnecter = (Button) findViewById(R.id.btn_SeConnecter);
        btnSeConnecter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_A_Inscription:
                Intent i= new Intent(this,Inscription.class);
                startActivity(i);
                break;
            case R.id.btn_SeConnecter:
                verificationConnexion();
                break;
        }
    }

    private void verificationConnexion(){
        EditText editUser  = (EditText)findViewById(R.id.champ_login);
        EditText editPassword   = (EditText)findViewById(R.id.champ_mdp);

        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Login), this);
        ser.connexion(editUser.getText().toString(), editPassword.getText().toString());
    }

    /* Vérifie si c'est la première fois qu'on ouvre l'app */
    private void premiereConnexion(){

    }

    @Override
    public void communication(JSONObject rep) {
        String con = new String();
        try {
            if(rep != null){
                con = rep.getString("response");
            } else {
                con = "Aucune réponse du serveur";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            con = "Réponse du serveur incorrect";
        }

        if(con == "true"){
            Intent i2= new Intent(this,Principal.class);
            startActivity(i2);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.DialogAlertStyle).create();
            alertDialog.setTitle("Connexion");
            alertDialog.setMessage("Mot de passe erroné!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        btnAInsc.setText(con);
    }
}
