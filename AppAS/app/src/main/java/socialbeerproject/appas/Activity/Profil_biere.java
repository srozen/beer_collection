package socialbeerproject.appas.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_biere);

        Bundle b = getIntent().getExtras();
        id = b.getString("id");

        sendServer(false);

        addListenerButton();
        addListenerOnRatingBar();
    }

    private void sendServer(Boolean refresh){
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_profilBiere), this);
        ser.profilBiere(id);

        if(!refresh){
            imgBouteille = (ImageView) findViewById(R.id.imageView_Bouteille);
            imgEtiquette = (ImageView) findViewById(R.id.imageView_Etiquette);
            ser.recuperationImage(id,imgBouteille,imgEtiquette);
        }
    }

    private void addListenerButton(){
        jeBois = (Button) findViewById(R.id.button_ajouterColl_biere);
        jeBois.setOnClickListener(this);
        retour = (Button) findViewById(R.id.button_retour_biere);
        retour.setOnClickListener(this);
    }

    private void addListenerOnRatingBar() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBarPer_biere);
        txtRatingValue = (TextView) findViewById(R.id.textView_prog_ratingBarPer_biere);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRatingValue.setText(String.valueOf(rating) + " / 10");
                if (jeBois.getVisibility() == View.GONE) {
                    jeBois.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ajouterColl_biere:
                /* **********************
                    ICI CODE POUR AJOUTER UNE BIERE A LA COLLECTION DE BIERE
                   **********************
                */
                this.sendServer(true);
                break;
            case R.id.button_retour_biere:
                finish();
                break;
        }
    }



    @Override
    public void communication(JSONObject rep) {
        /* **********************
              TODO : Modifier tous les champs de l'affichage selon la réponse du serveur
              (voir : http://46.101.143.168/beers/6.json) pour connaitre les champs
           **********************
        */
        if (rep.has("beer")){
            modifBiere(rep);
        } else if (rep.has("ajoutcoll")){
            this.sendServer(true);
        }
    }

    private void modifBiere(JSONObject rep){
        TextView name = (TextView) findViewById(R.id.textView_ProfilBiere_Title);
        TextView alcool = (TextView) findViewById(R.id.textView_alcool_biere);
        TextView brasserie = (TextView) findViewById(R.id.textView_brasserie_biere);
        TextView desc = (TextView) findViewById(R.id.textView_description_biere);
        TextView categorie = (TextView) findViewById(R.id.textView_types_biere);

        try {
            JSONObject beer = rep.getJSONObject("beer");
            JSONObject cat = rep.getJSONObject("category");

            name.setText(beer.getString("name"));
            alcool.setText(beer.getString("degree"));
            desc.setText(beer.getString("description"));

            categorie.setText(cat.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (false){
            modifDejaCollection();
        }
    }

    private void modifDejaCollection(){
        ProgressBar notePerPro = (ProgressBar) findViewById(R.id.prog_ratingPer_biere);
        TextView notePerTxt = (TextView) findViewById(R.id.textView_prog_ratingPer);
        notePerPro.setVisibility(View.VISIBLE);
        notePerTxt.setVisibility(View.VISIBLE);

        RelativeLayout ratingPer = (RelativeLayout) findViewById(R.id.relative_notePer_biere);
        ratingPer.setVisibility(View.GONE);

        jeBois.setText(R.string.btn_suppresionColl);
        jeBois.setVisibility(View.VISIBLE);
    }
}
