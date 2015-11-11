package socialbeerproject.appas.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private String idReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_biere);

        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        idReview = "";

        sendServer(false);

        addListenerButton();
        addListenerOnRatingBar();
    }

    private void sendServer(Boolean refresh){
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_profilBiere), this);

        SharedPreferences log = getSharedPreferences("Login", Context.MODE_PRIVATE);

        ser.profilBiere(id, log.getString("idUser", "0"));

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
                if (idReview == ""){
                    ajouterDCollection();
                } else {
                    deleteDCollection();
                }
                this.sendServer(true);
                break;
            case R.id.button_retour_biere:
                finish();
                break;
        }
    }

    private void ajouterDCollection() {
        EditText commentEdit = (EditText) findViewById(R.id.champ_comment_biere);
        RatingBar notePer = (RatingBar) findViewById(R.id.ratingBarPer_biere);
        SharedPreferences log = getSharedPreferences("Login", Context.MODE_PRIVATE);

        String comment = commentEdit.getText().toString();
        String note = Integer.toString(notePer.getProgress());
        String idUser = log.getString("idUser", "0");
        String hash = log.getString("hash","");

        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_profilBiere), this);
        ser.ajoutColl(this.id,idUser,hash,note,comment);
    }

    private void deleteDCollection(){
        SharedPreferences log = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String idUser = log.getString("idUser", "0");
        String hash = log.getString("hash","");

        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_profilBiere), this);
        ser.deleteColl(idUser,hash,idReview);
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
        } else if (rep.has("success")){
            try {
                if (!rep.getBoolean("success")){
                    this.messageErreur("Problème!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.sendServer(true);
        }
    }

    private void modifBiere(JSONObject rep){
        TextView name = (TextView) findViewById(R.id.textView_ProfilBiere_Title);
        TextView alcool = (TextView) findViewById(R.id.textView_alcool_biere);
        TextView brasserie = (TextView) findViewById(R.id.textView_brasserie_biere);
        TextView desc = (TextView) findViewById(R.id.textView_description_biere);
        TextView categorie = (TextView) findViewById(R.id.textView_types_biere);
        ProgressBar notePerGlo = (ProgressBar) findViewById(R.id.prog_ratingGlo_biere);
        TextView notePerTxt = (TextView) findViewById(R.id.textView_prog_ratingGlo);

        try {
            JSONObject beer = rep.getJSONObject("beer");
            JSONObject cat = rep.getJSONObject("category");
            if (rep.has("review") && rep.getJSONArray("review").length()==1){
                System.out.println("PERTE");
                modifDejaCollection(rep.getJSONArray("review").getJSONObject(0));
            } else {
                System.out.println("Aucune rev");
            }

            if (!beer.isNull("global_note") && !beer.getString("global_note").equals("null")){
                float noteGlo = (float) beer.getDouble("global_note");
                notePerGlo.setProgress((int) noteGlo*10);
                notePerTxt.setText(getString(R.string.texte_noteGlobal) + Float.toString(noteGlo) + " /10");
            } else {
                notePerTxt.setText("Aucune note encore attribuée! ");
            }

            name.setText(beer.getString("name"));
            alcool.setText(beer.getString("degree"));
            desc.setText(beer.getString("description"));

            categorie.setText(cat.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void modifDejaCollection(JSONObject review) throws JSONException {
        ProgressBar notePerPro = (ProgressBar) findViewById(R.id.prog_ratingPer_biere);
        TextView notePerTxt = (TextView) findViewById(R.id.textView_prog_ratingPer);
        notePerPro.setVisibility(View.VISIBLE);
        notePerTxt.setVisibility(View.VISIBLE);

        RelativeLayout ratingPer = (RelativeLayout) findViewById(R.id.relative_notePer_biere);
        ratingPer.setVisibility(View.GONE);

        jeBois.setText(R.string.btn_suppresionColl);
        jeBois.setVisibility(View.VISIBLE);

        float notePer = (float) review.getDouble("note");
        idReview = review.getString("id");

        notePerPro.setProgress((int) notePer*10);
        notePerTxt.setText(getString(R.string.texte_notePer) + Float.toString(notePer) + " /10" );
    }
}
