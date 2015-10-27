package socialbeerproject.appas.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Inscription extends ActivityCom implements View.OnClickListener{

    private Button btnAConn = null;
    private Button btnIns = null;
    private String username = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if (log.getString("username","n/a")!="n/a"){
            Intent i= new Intent(this,Login.class);
            startActivity(i);
        }

        btnAConn = (Button) findViewById(R.id.btn_A_SeConnecter);
        btnAConn.setOnClickListener(this);
        btnIns = (Button) findViewById(R.id.btn_confirmation);
        btnIns.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_A_SeConnecter:
                Intent i= new Intent(this,Login.class);
                startActivity(i);
                break;
            case R.id.btn_confirmation:
                if (chargementFini){
                    this.verificationInscription();
                }
                break;
        }
    }

    private void verificationInscription(){
        EditText editUser  = (EditText)findViewById(R.id.champ_login);
        EditText editPassword   = (EditText)findViewById(R.id.champ_mdp);
        EditText editMail  = (EditText)findViewById(R.id.champ_mail);

        username = editUser.getText().toString();
        password = editPassword.getText().toString();

        chargementFini=false;
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Inscription), this);
        ser.inscription(username, password , editMail.getText().toString());
    }

    @Override
    public void communication(JSONObject rep) {
        chargementFini=true;
        String valInscription = "";

        try {
            if(rep != null){
                if (rep.getString("checkUser") == "true" && rep.getString("checkMail") == "true"){
                    valInscription = "true";
                } else {
                    if ("checkUser" == "false"){
                        valInscription = "Username déjà utilisé";
                    } else if ("checkMail" == "false"){
                        valInscription = "Mail déjà utilisé";
                    }

                }
            } else {
                valInscription = "Aucune réponse du serveur";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            valInscription = "Réponse du serveur incorrect";
        }

        if(valInscription == "true"){
            this.connexionValide(username, password);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.DialogAlertStyle).create();
            alertDialog.setTitle("Inscription");
            alertDialog.setMessage(valInscription);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
