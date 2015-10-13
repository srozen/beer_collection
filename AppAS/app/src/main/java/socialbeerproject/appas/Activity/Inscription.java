package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import socialbeerproject.appas.R;

public class Inscription extends Activity implements View.OnClickListener{

    private Button btnAConn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        btnAConn = (Button) findViewById(R.id.btn_A_SeConnecter);
        btnAConn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inscription, menu);
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
}
