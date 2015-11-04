package socialbeerproject.appas.Fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import socialbeerproject.appas.Activity.Login;
import socialbeerproject.appas.Activity.Principal;
import socialbeerproject.appas.Activity.Profil;
import socialbeerproject.appas.Adaptateurs.AdaptateurMenuP;
import socialbeerproject.appas.Elements.ElementMenuP;
import socialbeerproject.appas.R;


public class MenuP extends ListFragment {
	
    private int Position = 0;
    private ArrayList<String> Item;
    
    AdaptateurMenuP adapter;
    private List<ElementMenuP> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.creationMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Choix", Position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    	selectItem(position, v);

    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void selectItem(int index, View v) {
        Position = index;

        getListView().setItemChecked(index, true);

        switch (index) {
            case 0:
                this.replaceFragment("Scan");
                break;
            case 1:
                this.replaceFragment("Collection");
                break;
            case 2:
                this.replaceFragment("Catalogue");
                break;
            case 3:
                startActivity(new Intent(getActivity(), Profil.class));
                break;
            case 4:
                break;
            case 5:

                break;
            case 6:
                this.logOut();
                break;
        }
    }

    void creationMenu(){
        element = new ArrayList<ElementMenuP>();

        ElementMenuP e1 = new ElementMenuP("Scan", "Scan ta binouze", R.string.ic_scan);
        ElementMenuP e2 = new ElementMenuP("Collection", "Toute les bières que tu as sifflé", R.string.ic_collection);
        ElementMenuP e3 = new ElementMenuP("Catalogue", "Liste de tous les bières", R.string.ic_catalogue);
        ElementMenuP e4 = new ElementMenuP("Profil", "Paramètres personnels", R.string.ic_profil);
        ElementMenuP e5 = new ElementMenuP("BeerMap", "Carte des buveurs", R.string.ic_map);
        ElementMenuP e6 = new ElementMenuP("Bons Plans", "Promo sur les bières", R.string.ic_bon_plan);
        ElementMenuP e7 = new ElementMenuP("Déconnexion", "Changement de compte?", R.string.ic_deco);

        element.add(e1);
        element.add(e2);
        element.add(e3);
        element.add(e4);
        element.add(e5);
        element.add(e6);
        element.add(e7);

        adapter = new AdaptateurMenuP(getActivity(), element);
        setListAdapter(adapter);
    }

    private void replaceFragment(String frag){
        //Remplace un fragment par un autre
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        Principal prin = (Principal) getActivity();
        switch (frag) {
            case "Scan":
                ft.replace(R.id.linear, new Scan());
                break;
            case "Collection":
                //ft.replace(R.id.linear, prin.collection);
                break;
            case "Catalogue":
                ft.replace(R.id.linear, prin.catalogue);
                break;
        }

        ft.addToBackStack(null);
        ft.commit();
    }

    private void logOut(){
        SharedPreferences log = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = log.edit();
        edit.remove("username");
        edit.remove("password");
        edit.apply();
        startActivity(new Intent(getActivity(), Login.class));
        getActivity().finish();
    }
}

