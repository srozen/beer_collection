package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import socialbeerproject.appas.Fragments.MenuP;
import socialbeerproject.appas.R;

public class Profil_biere extends Activity implements View.OnClickListener {

    private Button jeBois;
    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_biere);
        jeBois = (Button) findViewById(R.id.button_JeBois);
        jeBois.setOnClickListener(this);
        retour = (Button) findViewById(R.id.button_Retour);
        retour.setOnClickListener(this);

	/* ************************
		CHARGER LA BIERE
          ************************ */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_JeBois:
                jeBois.setText("J'ai bu !");
                /* **********************
                    ICI CODE POUR AJOUTER UNE BIERE A LA COLLECTION DE BIERE
                   **********************
                */
                break;
            case R.id.button_Retour:
                Intent i= new Intent(this,Principal.class);
                startActivity(i);
                break;
        }
    }
}
