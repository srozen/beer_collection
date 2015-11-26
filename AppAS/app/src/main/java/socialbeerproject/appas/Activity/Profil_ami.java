package socialbeerproject.appas.Activity;

/**
 * Created by Pierret on 05-11-15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Divers.MD5Util;
import socialbeerproject.appas.R;
import socialbeerproject.appas.SIP.Phone;
import socialbeerproject.appas.Serveur.ImageHTTP;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Profil_ami extends ActivityCom implements View.OnClickListener {

    private Button lookCollection;
    private Button back;
    private TextView title;
    private TextView saCollection;
    private String idAmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Bundle b = getIntent().getExtras();
        idAmi = b.getString("id");

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

        //Phone.getInstance().attach(this);
    }

    @Override
    public void finish(){
        super.finish();
        //Phone.getInstance().close();
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
            case R.id.imgBtn_call_profil:
                Intent intent = new Intent(this, PhoneA.class);

                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                intent.putExtra("id", this.idAmi);
                startActivity(intent);

                if (Phone.getInstance().status == "Ready"){
                    //Phone.getInstance().initiateCall(idAmi);
                }
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

            ImageButton callBtn = (ImageButton) findViewById(R.id.imgBtn_call_profil);
            callBtn.setVisibility(View.VISIBLE);
            callBtn.setOnClickListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.demandeGravatar(avatar, mail.getText().toString());
    }

    private void demandeGravatar(ImageView img, String mail){
        String hash = MD5Util.md5Hex(mail);
        new ImageHTTP(img).execute(ImageHTTP.cheminGravatar + hash + ".jpg?s=200");
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
