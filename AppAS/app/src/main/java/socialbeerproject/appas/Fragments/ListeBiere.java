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
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Activity.Profil_biere;
import socialbeerproject.appas.Adaptateurs.AdaptateurCollection;
import socialbeerproject.appas.Elements.ElementCollection;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class ListeBiere extends ListFragment {

    private int Position = 0;
    private ArrayList<String> Item;

    private View previous;

    AdaptateurCollection adapter;
    private List<ElementCollection> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.linear),(ActivityCom) getActivity());
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
        String tag = this.getTag();
        Log.d(tag, "id, position " + id + " " + position);
        previous.setSelected(false);

        v.setSelected(true);

        previous=v;

        selectItem(position, v);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void selectItem(int index, View v) {
        Position = index;
        // We can display everything in-place with fragments, so update
        // the list to highlight the selected item and show the data.
        getListView().setItemChecked(index, true);

        Intent intent = new Intent(getActivity(), Profil_biere.class);

        //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
        intent.putExtra("id", element.get(index).getId());
        startActivity(intent);
        getActivity().overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
    }

    public void creationListe(JSONObject rep) throws JSONException {
        element = new ArrayList<ElementCollection>();
        /*
        TODO: enregister les rating dans l'element
        */
        JSONArray reviews = new JSONArray();

        if (rep.has("reviews") && !rep.getJSONArray("reviews").equals(null)){
            reviews = rep.getJSONArray("reviews");
        } else {
            reviews = null;
        }
        if(rep != null){
            int nbBiere=0;
            nbBiere = rep.getJSONArray("beers").length();
            for (int i=0;i<nbBiere;i++){
                ElementCollection elementBiere = creationElement(rep.getJSONArray("beers").getJSONObject(i), reviews);
                element.add(elementBiere);
            }
        } else {
            ActivityCom activiteCom  = (ActivityCom) getActivity();
            activiteCom.messageErreur("Aucune connexion au serveur possible!");
        }
        adapter = new AdaptateurCollection(getActivity(), element);
        setListAdapter(adapter);
    }

    public ElementCollection creationElement(JSONObject biere, JSONArray reviews){
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
        ElementCollection elementBiere = new ElementCollection(nom, R.mipmap.ic_launcher, id);
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