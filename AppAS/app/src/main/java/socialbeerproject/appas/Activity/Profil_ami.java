package socialbeerproject.appas.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import socialbeerproject.appas.Fragments.ListeBiere;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ImageHTTP;
import socialbeerproject.appas.Serveur.ServeurCom;

/**
 * Classe Profil_ami, cette activité permet de montrer le profil d'un utilisateur
 * @author Voet Rémy, Faignaert Florian, Pierret Cyril
 */

public class Profil_ami extends ActivityCom implements View.OnClickListener {

    private Button lookCollection;
    private Button back;
    private TextView title;
    private TextView saCollection;
    private String idAmi;
    private ListeBiere collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Bundle b = getIntent().getExtras();
        idAmi = b.getString("id");

        /* BOUTON A EFFACER */
        Button delete1 = (Button) findViewById(R.id.button_Chg_Pass);
        Button delete3 = (Button) findViewById(R.id.button_Friend_Add);

        delete1.setVisibility(View.GONE);
        delete3.setVisibility(View.GONE);

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

    // ATTENTION !!!! LE NOM DES BOUTONS DES RESSOURCES NE CONCORDENT PAS AVEC LES BOUTONS
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.button_Chg_Avatar :
                this.openFriendCollection();
                break;
            case R.id.button_retour_profil:
                finish();
                break;
            case R.id.imgBtn_call_profil:
                Intent intent = new Intent(this, PhoneA.class);

                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                intent.putExtra("id", this.idAmi);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void communication(JSONObject rep) {
        if(rep != null && rep.has("login")){
            this.modifProfilAmi(rep);
        } else if (rep.has("beers")){
            try {
                RelativeLayout relListe = (RelativeLayout) findViewById(R.id.rel_coll_ami);
                relListe.setVisibility(View.VISIBLE);
                collection.creationListe(rep);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void finish(){
        super.finish();
    }

    /**
     * demandeProfil : demande au serveur le Profil de l'idAmi
     */

    private void demandeProfil(){
        Bundle args = getIntent().getExtras();
        if (idAmi !=null){
            ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_titre_profil), this);
            ser.profil(idAmi);
        }
    }

    /**
     * modifProfilAmi : modifie les informations du layout xml par rapport aux données de l'ami
     * @param rep : JSON répondu par le serveur
     */

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

    /**
     * demandeGravatar : demande de l'image du profil
     * @param img : l'image
     * @param mail : le mail du user
     */

    private void demandeGravatar(ImageView img, String mail){
        String hash = MD5Util.md5Hex(mail);
        new ImageHTTP(img).execute(ImageHTTP.cheminGravatar + hash + ".jpg?s=200");
    }

    /**
     * openFriendCollection : affiche la collection de bière de l'ami
     */

    public void openFriendCollection() {
        collection = new ListeBiere();
        Bundle args = new Bundle();
        args.putString("type", "ami");
        args.putString("idAmi", idAmi);
        collection.setArguments(args);

        if(getFragmentManager().findFragmentById(R.id.rel_coll_ami) == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.rel_coll_ami, collection);
            ft.commit();
        }
    }
}
