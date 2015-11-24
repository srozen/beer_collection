package socialbeerproject.appas.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Inscription extends ActivityCom implements View.OnClickListener{

    private Button btnAConn = null;
    private Button btnIns = null;
    private String username = null;
    private String password = null;
    private String mail = null;

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
        mail = editMail.getText().toString();

        if (this.verifChamp()){
            chargementFini = false;
            ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Inscription), this);
            ser.inscriptionOne(username, password , mail);
        }
    }

    private boolean verifChamp() {

        boolean work = false;

        // ON CHECK L'ADRESSE MAIL EN PREMIER
        if ( (this.mail.length() > 6) && (isEmailValid()) ){
            // ON CHECK LE NOM UTILISATEUR
            if (isUserValid()) {
                // ON CHECK LE MOT DE PASSE
                if (isPassValid())
                    work = true;
                else {
                    // MSG ERREUR - nom d'utilisateur incorrecte
                    String errorMessage = "Whoops - le mot de passe doit être formé de 6 à 40 caractères";
                    super.prinToast(errorMessage);
                }

            } else {
                // MSG ERREUR - nom d'utilisateur incorrecte
                String errorMessage = "Whoops - le nom d'utilisateur doit être formé de 4 à 50 caractères";
                super.prinToast(errorMessage);
            }
        } else {
            // MSG ERREUR - mail trop petit
            String errorMessage = "Whoops - l'adresse mail est incorrecte - Réessayez.";
            super.prinToast(errorMessage);
        }

        return work;
    }

    public boolean isPassValid() {
        return (this.password.length() >= 6) && (this.password.length() <= 40);
    }

    public boolean isUserValid() {
        return (this.username.length() >= 4) && (this.username.length() <= 50);
    }

    public boolean isEmailValid() {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = this.mail;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    @Override
    public void communication(JSONObject rep) {
        chargementFini = true;
        String erreurInsc = "";

        try {
            if(rep != null){
                if (rep.getString("checkUser") != "true" && rep.getString("checkMail") != "true") {
                    if ("checkUser" == "false") {
                        erreurInsc = "Username déjà utilisé";
                    } else if ("checkMail" == "false") {
                        erreurInsc = "Mail déjà utilisé";
                    } else {
                        erreurInsc = "Mail et Username déjà utilisé";
                    }
                }
            } else {
                erreurInsc = "Aucune réponse du serveur";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            erreurInsc = "Réponse du serveur incorrect";
        }

        if(erreurInsc.isEmpty()){
            try {
                super.setIdUser(rep.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.connexionValide(username);
        } else {
            this.messageErreur(erreurInsc);
        }
    }
}
