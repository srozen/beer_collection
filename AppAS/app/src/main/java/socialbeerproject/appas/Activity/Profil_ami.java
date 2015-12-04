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

<<<<<<< HEAD
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import socialbeerproject.appas.R;


import org.json.JSONObject;

import socialbeerproject.appas.R;

public class Profil_ami extends ActivityCom implements View.OnClickListener {

    private Button delete1;
    private Button delete2;
    private Button addFriend;
    private Button lookFriend;
=======
public class Profil_ami extends ActivityCom implements View.OnClickListener {

>>>>>>> develop_android
    private Button lookCollection;
    private Button back;
    private TextView title;
    private TextView saCollection;
<<<<<<< HEAD
=======
    private String idAmi;
    private ListeBiere collection;
>>>>>>> develop_android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

<<<<<<< HEAD
        /* BOUTON A EFFACER */
        delete1 = (Button) findViewById(R.id.button_Chg_Pass);
        delete1.setVisibility(View.INVISIBLE);
        delete2 = (Button) findViewById(R.id.button_Chg_Mail);
        delete2.setVisibility(View.INVISIBLE);

        addFriend = (Button) findViewById(R.id.button_Chg_Avatar);
        addFriend.setText("Ajouter cet ami");
        addFriend.setOnClickListener(this);

         /* TODO : SI EST DEJA AMI */
        lookFriend = (Button) findViewById(R.id.button_Friend_Add);
        lookFriend.setText("Voir ses amis");
        lookFriend.setOnClickListener(this);

        /* TODO : SI EST DEJA AMI */
        lookCollection = (Button) findViewById(R.id.button_Friends_View);
        lookCollection.setOnClickListener(this);
        lookCollection.setText("Voir sa collection");


        /* CHANGEMENT DE TITRE */
        title = (TextView) findViewById(R.id.texte_Profil);
=======
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
>>>>>>> develop_android
        title.setText("Profil d'un ami");
        title.setTextSize(25);

        /* CHANGEMENT DU LABEL MA COLLECTION PAR SA COLLECTION */
<<<<<<< HEAD
        saCollection = (TextView) findViewById(R.id.texte_MaCol);
        saCollection.setText("Sa collection :");


        back = (Button) findViewById(R.id.button_retour_profil);
        back.setOnClickListener(this);
    }

=======
        saCollection = (TextView) findViewById(R.id.textView_titreCollection_profil);
        saCollection.setText("Sa collection :");

        back = (Button) findViewById(R.id.button_retour_profil);
        back.setOnClickListener(this);

        this.demandeProfil();
    }
>>>>>>> develop_android

    // ATTENTION !!!! LE NOM DES BOUTONS DES RESSOURCES NE CONCORDENT PAS AVEC LES BOUTONS
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.button_Chg_Avatar :
<<<<<<< HEAD
                // TODO : ENVOYEZ LA REQUETE AU SERVEUR d'ajout d'ami
                break;
            case R.id.button_Friend_Add :
                // TODO : VOIR SA LISTE D AMI
                break;
            case R.id.button_Friends_View:
                //  TODO : VOIR SA COLLECTION
                break;

            case R.id.button_retour_profil:
                finish();
                break;
=======
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
>>>>>>> develop_android
        }
    }

    @Override
    public void communication(JSONObject rep) {
<<<<<<< HEAD

=======
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
>>>>>>> develop_android
    }
}
