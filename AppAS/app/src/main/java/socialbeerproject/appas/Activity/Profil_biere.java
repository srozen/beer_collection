package socialbeerproject.appas.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Profil_biere extends ActivityCom implements View.OnClickListener {

    private Button jeBois;
    private Button retour;
    private ImageView imgBouteille;
    private ImageView imgEtiquette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_biere);
        jeBois = (Button) findViewById(R.id.button_JeBois);
        jeBois.setOnClickListener(this);
        retour = (Button) findViewById(R.id.button_Retour);
        retour.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        String id = b.getString("id");

        imgBouteille = (ImageView) findViewById(R.id.imageView_Bouteille);
        imgEtiquette = (ImageView) findViewById(R.id.imageView_Etiquette);

        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_profilBiere), this);
        ser.profilBiere(id);
        ser.recuperationImage(id,imgBouteille,imgEtiquette);
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
                finish();
                break;
        }
    }

    @Override
    public void communication(JSONObject rep) {
        /* **********************
              TODO : Modifier tous les champs de l'affichage selon la réponse du serveur
           **********************
        */

        TextView name = (TextView) findViewById(R.id.textView_ProfilBiere_Title);
        try {
            JSONObject beer = rep.getJSONObject("beer");
            name.setText(beer.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
