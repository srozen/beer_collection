package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Elements.ElementPlan;
import socialbeerproject.appas.Adaptateurs.AdaptateurPlan;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class BonPlan extends ListFragment {

    private int position = 0;
    private View previous;

    AdaptateurPlan adapter;
    private List<ElementPlan> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.demandeServeur();

        previous = new View(getActivity().getApplicationContext());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Choix", position);
    }


    private void demandeServeur() {
        ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.rel_menu), (ActivityCom) getActivity());

        ser.getBonPlan();
    }

    public void creationListe(JSONObject rep) throws JSONException {
        element = new ArrayList<ElementPlan>();

        if(rep != null){
            int nbPlan=0;
            nbPlan = rep.getJSONArray("bonplan").length();
            for (int i=0;i<nbPlan;i++){
                ElementPlan elementPlan = creationElement(rep.getJSONArray("bonplan").getJSONObject(i));
                element.add(elementPlan);
            }
        } else {
            ActivityCom activiteCom  = (ActivityCom) getActivity();
            activiteCom.messageErreur("Aucune connexion au serveur possible!");
        }
        adapter = new AdaptateurPlan(getActivity(), element);
        setListAdapter(adapter);
    }

    public ElementPlan creationElement(JSONObject bonplan){
        String title = "";
        String nomBiere = "";
        String description= "";
        String categorie="";
        String prix="";
        String dateDebut= "";
        String dateFin= "";
        String reference= "";
        String id= "";

        try {
            title = bonplan.getString("name");
            nomBiere = bonplan.getString("name_beer");
            description = bonplan.getString("description");
            categorie = bonplan.getString("categorie");
            prix = bonplan.getString("prix");
            dateDebut = bonplan.getString("datedebut");
            dateFin = bonplan.getString("datefin");
            reference = bonplan.getString("reference");
            id = bonplan.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ElementPlan elementPlan = new ElementPlan(title,nomBiere,description,categorie,prix,dateDebut,dateFin,reference,id);

        return elementPlan;
    }

}


