package socialbeerproject.appas.Activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import socialbeerproject.appas.Chargement;
import socialbeerproject.appas.R;
import socialbeerproject.appas.ServeurCom;

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

                btnAInsc.setText(ServeurCom.connexion("er","dsf"));
                //Chargement c = new Chargement(this, (RelativeLayout) findViewById(R.id.lnr_Login));
                //c.start();
                //Intent i= new Intent(this,Inscription.class);
                //startActivity(i);

                break;
            case R.id.btn_SeConnecter:
                this.verificationConnexion();
                break;
        }
    }

    private void verificationConnexion(){
        Intent i= new Intent(this,Principal.class);
        startActivity(i);
    }
}
