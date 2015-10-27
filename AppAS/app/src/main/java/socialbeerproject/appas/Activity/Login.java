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

public class Login extends ActivityCom implements View.OnClickListener{

    private Button btnAInsc = null;
    private Button btnSeConnecter = null;
    private String username = null;
    private String password = null;

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
                    this.launchConnexion();
                }
                break;
        }
    }

    /* Vérifie si c'est la première fois qu'on ouvre l'app, si non connexion automatique, sinon
    * Go inscription */
    private void premiereConnexion(){
        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if (log.getString("username","n/a")!="n/a"){

            EditText editUser = (EditText) findViewById(R.id.champ_login);
            editUser.setText(log.getString("username", "n/a"));

            EditText editPassword = (EditText) findViewById(R.id.champ_mdp);
            editPassword.setText(log.getString("password", "n/a"));

            this.launchConnexion();
        }
    }

    /* Lance la connexion */
    private void launchConnexion(){
        EditText editUser  = (EditText)findViewById(R.id.champ_login);
        EditText editPassword   = (EditText)findViewById(R.id.champ_mdp);

        username = new String(editUser.getText().toString());
        password = new String(editPassword.getText().toString());

        chargementFini = false;
        
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.lnr_Login), this);
        ser.connexion(editUser.getText().toString(), editPassword.getText().toString());
    }



    @Override
    public void communication(JSONObject rep) {
        chargementFini = true;
        String connexion = new String();

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
            this.connexionValide(username,password);
        } else {
            if(connexion == "false"){
                connexion = "Mot de passe erroné!";
            }
            AlertDialog alertDialog = new AlertDialog.Builder(this,R.style.DialogAlertStyle).create();
            alertDialog.setTitle("Connexion");
            alertDialog.setMessage(connexion);
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
