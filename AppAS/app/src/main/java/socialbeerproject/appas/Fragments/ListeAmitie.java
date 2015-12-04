package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Activity.Principal;
import socialbeerproject.appas.Activity.Profil_ami;
import socialbeerproject.appas.Activity.Profil_biere;
import socialbeerproject.appas.Adaptateurs.AdaptateurAmitie;
import socialbeerproject.appas.Adaptateurs.AdaptateurListeBiere;
import socialbeerproject.appas.Elements.ElementAmitie;
import socialbeerproject.appas.Elements.ElementListeBiere;
import socialbeerproject.appas.Elements.ElementPlan;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

/**
 * Classe ListeAmitie, cette classe permet de lister toutes les amitiées
 * @author Voet Rémy, Faignaert Florian, Pierret Cyril
 */

public class ListeAmitie extends ListFragment {

    private int Position = 0;
    private ArrayList<String> Item;
    private View previous;
    AdaptateurAmitie adapter;
    private List<ElementAmitie> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.demandeServeur();

        previous = new View(getActivity().getApplicationContext());
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView title = (TextView) getActivity().findViewById(R.id.titre_principal);
        title.setText("Vos ami(e)s");
        ImageButton imgButton = (ImageButton) getActivity().findViewById(R.id.btn_principal);
        imgButton.setVisibility(View.VISIBLE);
        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Principal prin = (Principal) getActivity();
                prin.replaceFragment("Menu");
                prin.closeFrag(prin.amitie);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Choix", Position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        String tag = this.getTag();

        previous.setSelected(false);
        //previous.setBackgroundColor(Color.TRANSPARENT);
        v.setSelected(true);
        //v.setBackgroundColor(R.color.blue);
        previous=v;

        selectItem(position, v);
    }

    public void selectItem(int index, View v) {

        Position = index;
        // We can display everything in-place with fragments, so update
        // the list to highlight the selected item and show the data.
        getListView().setItemChecked(index, true);

        Intent intent = new Intent(getActivity(), Profil_ami.class);

        //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
        intent.putExtra("id", element.get(index).getId());
        startActivity(intent);
        getActivity().overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
    }

    /**
     * demandeServeur : demande au serveur la liste d'amitié
     */

    private void demandeServeur() {

        ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.rel_menu),(ActivityCom) getActivity());
        ActivityCom activiteCom = (ActivityCom) getActivity();
        SharedPreferences log = activiteCom.getSharedPreferences("Login", Context.MODE_PRIVATE);

        ser.listeAmi(log.getString("idUser", "n/a"));

    }

    /**
     * creationListe : Créé la liste à partir de la réponse reçu par le serveur
     * @param rep : réponse du serveur
     * @throws JSONException
     */

    public void creationListe(JSONObject rep) throws JSONException {
        element = new ArrayList<ElementAmitie>();

        if(rep != null && rep.has("friends")){
            int nbAmi=0;
            nbAmi = rep.getJSONArray("friends").length();
            for (int i=0;i<nbAmi;i++){
                ElementAmitie elementAmi = creationAmi(rep.getJSONArray("friends").getJSONObject(i));
                element.add(elementAmi);
            }
        } else {
            ActivityCom activiteCom  = (ActivityCom) getActivity();
            activiteCom.messageErreur("Aucune connexion au serveur possible !");
        }
        adapter = new AdaptateurAmitie(getActivity(), element);
        setListAdapter(adapter);
    }

    /**
     * creationAmi, traduit un JSON en ElementAmi
     * @param ami : l'ami en JSON
     * @return : le JSON convertit en ElementAmi
     */

    public ElementAmitie creationAmi(JSONObject ami){
        String nom = "";
        String id=  "";

        try {
            nom = ami.getString("login");
            id = ami.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ElementAmitie elementAmi = new ElementAmitie(nom, R.mipmap.ic_profil, id);

        return elementAmi;
    }
}