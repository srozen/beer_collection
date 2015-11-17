package socialbeerproject.appas.Activity;

/**
 * Created by Pierret on 05-11-15.
 */

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
    private Button addFriend;
    private Button lookFriend;
    private Button lookCollection;
    private Button back;
    private TextView title;
    private TextView saCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        /* BOUTON A EFFACER */
        delete1 = (Button) findViewById(R.id.button_Chg_Pass);
        delete1.setVisibility(View.INVISIBLE);

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
        title = (TextView) findViewById(R.id.textView_titre_profil);
        title.setText("Profil d'un ami");
        title.setTextSize(25);

        /* CHANGEMENT DU LABEL MA COLLECTION PAR SA COLLECTION */
        saCollection = (TextView) findViewById(R.id.textView_titreCollection_profil);
        saCollection.setText("Sa collection :");


        back = (Button) findViewById(R.id.button_retour_profil);
        back.setOnClickListener(this);
    }


    // ATTENTION !!!! LE NOM DES BOUTONS DES RESSOURCES NE CONCORDENT PAS AVEC LES BOUTONS
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.button_Chg_Avatar :
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
        }
    }

    @Override
    public void communication(JSONObject rep) {

    }
}
