package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

        ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.linear),(ActivityCom) getActivity());
        ser.catalogue();

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
        //previous.setBackgroundColor(Color.TRANSPARENT);
        v.setSelected(true);
        //v.setBackgroundColor(R.color.blue);
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
        if(rep != null){
            int nbBiere=0;
            nbBiere = rep.getJSONArray("beers").length();
            for (int i=0;i<nbBiere;i++){
                creationElement(rep.getJSONArray("beers").getJSONObject(i));
            }
        } else {
            ActivityCom activiteCom  = (ActivityCom) getActivity();
            activiteCom.messageErreur("Aucune connexion au serveur possible!");
        }
        adapter = new AdaptateurCollection(getActivity(), element);
        setListAdapter(adapter);
    }

    public void creationElement(JSONObject biere){
        String nom = "";
        String id= "";
        try {
            nom = biere.getString("name");
            id = biere.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        element.add(new ElementCollection(nom, R.mipmap.ic_launcher, id));
    }
}