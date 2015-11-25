package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import socialbeerproject.appas.Activity.Profil_biere;
import socialbeerproject.appas.Adaptateurs.AdaptateurListeBiere;
import socialbeerproject.appas.Elements.ElementListeBiere;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class ListeBiere extends ListFragment {

    private int Position = 0;

    private View previous;

    AdaptateurListeBiere adapter;
    private List<ElementListeBiere> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.demandeServeur();

        previous = new View(getActivity().getApplicationContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Choix", Position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        previous.setSelected(false);
        v.setSelected(true);
        previous=v;

        selectItem(position);
    }

    void selectItem(int index) {
        Position = index;
        // Met à jour la ListView
        getListView().setItemChecked(index, true);
        Intent intent = new Intent(getActivity(), Profil_biere.class);

        // Avec en paramètre l'id de la vue de l'activité mère et un fragment.
        intent.putExtra("id", element.get(index).getId());
        startActivity(intent);
        getActivity().overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
    }

    @Override
         public void onResume(){
        super.onResume();
        this.demandeServeur();
        this.makeTitle();
    }

    private void makeTitle(){
        Bundle args = getArguments();
        if (args!=null && args.getString("type","N/A") != "N/A" ) {
            TextView title = (TextView) getActivity().findViewById(R.id.titre_principal);
            ImageButton imgButton = (ImageButton) getActivity().findViewById(R.id.btn_principal);
            imgButton.setVisibility(View.VISIBLE);
            switch (args.getString("type","N/A")) {
                case "catalogue":
                    title.setText("Catalogue");
                    imgButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Principal prin = (Principal) getActivity();
                            prin.replaceFragment("Menu");
                            prin.closeFrag(prin.catalogue);
                        }
                    });
                    break;
                case "collection":
                    title.setText("Votre collection");
                    imgButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Principal prin = (Principal) getActivity();
                            prin.replaceFragment("Menu");
                            prin.closeFrag(prin.collection);
                        }
                    });
                    break;
            }
        }
    }

    private void demandeServeur(){
        Bundle args = getArguments();
        ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.rel_menu),(ActivityCom) getActivity());
        if (args!=null && args.getString("type","N/A") != "N/A" ) {
            ActivityCom activiteCom = (ActivityCom) getActivity();
            SharedPreferences log = activiteCom.getSharedPreferences("Login", Context.MODE_PRIVATE);
            switch (args.getString("type","N/A")) {
                case "catalogue":
                    ser.catalogue(log.getString("idUser", "n/a"));
                    break;
                case "collection":
                    ser.collection(log.getString("idUser", "n/a"));
                    break;

            }
        }
    }

    public void creationListe(JSONObject rep) throws JSONException {
        element = new ArrayList<ElementListeBiere>();
        JSONArray reviews = new JSONArray();

        if (rep.has("reviews") && !rep.getJSONArray("reviews").equals(null)){
            reviews = rep.getJSONArray("reviews");
        } else {
            reviews = null;
        }
        if(rep != null && rep.has("beers")){
            int nbBiere=0;
            nbBiere = rep.getJSONArray("beers").length();
            for (int i=0;i<nbBiere;i++){
                ElementListeBiere elementBiere = creationElement(rep.getJSONArray("beers").getJSONObject(i), reviews);
                element.add(elementBiere);
            }
        } else {
            ActivityCom activiteCom  = (ActivityCom) getActivity();
            activiteCom.messageErreur("Aucune connexion au serveur possible!");
        }
        adapter = new AdaptateurListeBiere(getActivity(), element);
        setListAdapter(adapter);
    }

    public ElementListeBiere creationElement(JSONObject biere, JSONArray reviews){
        String nom = "";
        String id= "";
        float ratingPer = -1f ;
        float ratingGlo = -1f ;
        try {
            nom = biere.getString("name");
            id = biere.getString("id");
            if (reviews != null){
                ratingPer = findReview(biere, reviews);
            }
            if (!biere.getString("global_note").equals("null")){
                ratingGlo = Float.parseFloat(biere.getString("global_note"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ElementListeBiere elementBiere = new ElementListeBiere(nom, R.mipmap.ic_launcher, id);
        elementBiere.setRatingPer(ratingPer);
        elementBiere.setRatingGlo(ratingGlo);
        return elementBiere;
    }

    /**
     * Va chercher la note personnel grâce aux reviews
     * @param biere
     * @param reviews
     * @return
     */
    private float findReview(JSONObject biere, JSONArray reviews) {
        int nbReview;
        nbReview = reviews.length();
        for (int i=0;i<nbReview;i++){
            try {
                int idBeer = reviews.getJSONObject(i).getInt("beer_id");
                if (biere.getInt("id") == idBeer){
                    return (float) reviews.getJSONObject(i).getDouble("note");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return (-1f);
            }
        }
        return (-1f);
    }
}