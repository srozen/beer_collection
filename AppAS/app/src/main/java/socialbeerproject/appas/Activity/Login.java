package socialbeerproject.appas.Activity;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import socialbeerproject.appas.Divers.Chargement;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Login extends Activity implements View.OnClickListener{

    private Button btnAInsc = null;
    private Button btnSeConnecter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnAInsc = (Button) findViewById(R.id.btn_A_Inscription);
        btnAInsc.setOnClickListener(this);
        btnSeConnecter = (Button) findViewById(R.id.btn_SeConnecter);
        btnSeConnecter.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_A_Inscription:
                Intent i= new Intent(this,Inscription.class);
                startActivity(i);
                break;
            case R.id.btn_SeConnecter:
                if(verificationConnexion()){
                    Intent i2= new Intent(this,Principal.class);
                    startActivity(i2);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
                break;
        }
    }

    private boolean verificationConnexion(){
        Chargement.getInstance().attach(this, (RelativeLayout) findViewById(R.id.lnr_Login));
        Chargement.getInstance().start();

        EditText editUser  = (EditText)findViewById(R.id.champ_login);
        EditText editPassword   = (EditText)findViewById(R.id.champ_mdp);

        String con = ServeurCom.connexion(editUser.getText().toString(), editPassword.getText().toString());
        btnAInsc.setText(con);

        Chargement.getInstance().stop();
        Chargement.getInstance().dettach();

        if(con == "true"){
            return true;
        } else {
            return false;
        }
    }
}
