package socialbeerproject.appas.Activity;

/**
 * Created by Pierret on 05-11-15.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Divers.MD5Util;
import socialbeerproject.appas.Fragments.ListeBiere;
import socialbeerproject.appas.R;


import org.json.JSONObject;

import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ImageHTTP;
import socialbeerproject.appas.Serveur.ServeurCom;

import static android.app.PendingIntent.getActivity;

public class Profil_ami extends ActivityCom implements View.OnClickListener {

    private Button lookCollection;
    private Button back;
    private TextView title;
    private TextView saCollection;
    private String idAmi;

    public void setIdAmi(String news) {
        idAmi = news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        /* BOUTON A EFFACER */
        Button delete1 = (Button) findViewById(R.id.button_Chg_Pass);
        Button delete2 = (Button) findViewById(R.id.button_Friends_View);
        Button delete3 = (Button) findViewById(R.id.button_Friend_Add);

        delete1.setVisibility(View.INVISIBLE);
        delete2.setVisibility(View.INVISIBLE);
        delete3.setVisibility(View.INVISIBLE);

        lookCollection = (Button) findViewById(R.id.button_Chg_Avatar);
        lookCollection.setText("Voir sa collection");
        lookCollection.setOnClickListener(this);




        /* CHANGEMENT DE TITRE */
        title = (TextView) findViewById(R.id.textView_titre_profil);
        title.setText("Profil d'un ami");
        title.setTextSize(25);

        /* CHANGEMENT DU LABEL MA COLLECTION PAR SA COLLECTION */
        saCollection = (TextView) findViewById(R.id.textView_titreCollection_profil);
        saCollection.setText("Sa collection :");


        back = (Button) findViewById(R.id.button_retour_profil);
        back.setOnClickListener(this);

        this.demandeProfil();
    }

    private void demandeProfil(){
        Bundle args = getIntent().getExtras();
        if (args.getString("id","N/A") != "N/A"){
            ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_titre_profil), this);
            ser.profil(args.getString("id","N/A"));
        }
    }


    // ATTENTION !!!! LE NOM DES BOUTONS DES RESSOURCES NE CONCORDENT PAS AVEC LES BOUTONS
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.button_Chg_Avatar :
                this.openFriendCollection();

            case R.id.button_retour_profil:
                finish();
                break;
        }
    }

    private void modifProfilAmi (JSONObject rep){
        TextView username = (TextView) findViewById(R.id.textView_username_profil);
        TextView txtProgColl = (TextView) findViewById(R.id.textView_valeurCollection_profil);
        ProgressBar progressColl = (ProgressBar) findViewById(R.id.progress_collection_profil);
        ImageView avatar = (ImageView) findViewById(R.id.img_avatar_profil);
        TextView mail = (TextView) findViewById(R.id.textView_mail_profil);

        try {
            username.setText(rep.getString("login"));
            mail.setText(rep.getString("email"));
            txtProgColl.setText(rep.getString("nbBeers") + "/" + rep.getString("totalBeers"));
            progressColl.setProgress(Profil.getValuePro(rep.getInt("nbBeers"), rep.getInt("totalBeers")));
            idAmi = rep.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.demandeGravatar(avatar, mail.getText().toString());
    }

    private void demandeGravatar(ImageView img, String mail){
        String hash = MD5Util.md5Hex(mail);
        new ImageHTTP(img).execute(ImageHTTP.cheminGravatar + hash + ".jpg?s=200");
        System.out.println(hash);
    }

    @Override
    public void communication(JSONObject rep) {
        if(rep != null && rep.has("login")){
            this.modifProfilAmi(rep);
        }
    }

    public void openFriendCollection() {



    }
}
