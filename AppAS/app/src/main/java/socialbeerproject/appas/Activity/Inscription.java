package socialbeerproject.appas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import socialbeerproject.appas.R;

public class Inscription extends ActivityCom implements View.OnClickListener{

    private Button btnAConn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        btnAConn = (Button) findViewById(R.id.btn_A_SeConnecter);
        btnAConn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_A_SeConnecter:
                Intent i= new Intent(this,Login.class);
                startActivity(i);
                break;
            case R.id.btn_confirmation:
                this.verificationInscription();
                break;
        }
    }

    private void verificationInscription(){

    }

    @Override
    public void communication(JSONObject rep) {

    }
}
