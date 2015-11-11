package socialbeerproject.appas.Activity;

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

public class Login extends ActivityCom implements View.OnClickListener{

    private Button btnAInsc = null;
    private Button btnSeConnecter = null;
    private String username = null;

    /*
     * TODO: enregister le hash et pas le mot de passe, et envoyer directement le hash.
     */

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
                if (chargementFini){
                    this.launchConnexionFirst();
                }
                break;
        }
    }

    /* Vérifie si c'est la première fois qu'on ouvre l'app, sinon connexion automatique, sinon
    * Go inscription */
    private void premiereConnexion(){
        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if (log.getString("username","n/a")!="n/a"){
            EditText editUser = (EditText) findViewById(R.id.champ_login);
            editUser.setText(log.getString("username", "n/a"));
            this.launchConnexionAuto(log.getString("idUser","n/a"),log.getString("hash","n/a"),log.getString("username","n/a"));
        }
    }

    /* Lance la connexion */
    private void launchConnexionFirst(){
        EditText editUser  = (EditText)findViewById(R.id.champ_login);
        EditText editPassword   = (EditText)findViewById(R.id.champ_mdp);

        username = new String(editUser.getText().toString());

        chargementFini = false;
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Login), this);
        ser.connexionOne(editUser.getText().toString(), editPassword.getText().toString());
    }

    private void launchConnexionAuto(String id,String hash,String username){
        this.username = username;

        chargementFini = false;
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Login), this);
        ser.connexionTwo(hash, id);
    }

    @Override
    public void communication(JSONObject rep) {
        chargementFini = true;
        String connexion;
        try {
            if(rep != null){
                connexion = rep.getString("checkLog");
            } else {
                connexion = "Aucune réponse du serveur";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            connexion = "Réponse du serveur incorrect";
        }

        if(connexion == "true"){
            this.connexionValide(username);
        } else {
            if(connexion == "false"){
                connexion = "Mot de passe erroné!";
            }
            this.messageErreur(connexion);
        }
    }
}
