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
import socialbeerproject.appas.Activity.Profil;
import socialbeerproject.appas.Adaptateurs.Adaptateur;
import socialbeerproject.appas.Elements.ElementListe;
import socialbeerproject.appas.R;


public class MenuP extends ListFragment {
	
    private int Position = 0;
    private ArrayList<String> Item;
    
    Adaptateur adapter;
    private List<ElementListe> element;

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

                break;
            case 3:
                startActivity(new Intent(getActivity(), Profil.class));
                break;
            case 4:
                break;
            case 5:
                this.logOut();
                break;
        }
    }

    void creationMenu(){
        element = new ArrayList<ElementListe>();

        ElementListe e1 = new ElementListe("Scan", "Scan ta binouze", R.string.ic_scan);
        ElementListe e2 = new ElementListe("Collection", "Toute les bières que tu as sifflé", R.string.ic_collection);
        ElementListe e3 = new ElementListe("Catalogue", "Liste de tous les bières", R.string.ic_catalogue);
        ElementListe e4 = new ElementListe("Profil", "Paramètres personnels", R.string.ic_profil);
        ElementListe e5 = new ElementListe("BeerMap", "Carte des buveurs", R.string.ic_map);
        ElementListe e6 = new ElementListe("Déconnexion", "Changement de compte?", R.string.ic_deco);
        element.add(e1);
        element.add(e2);
        element.add(e3);
        element.add(e4);
        element.add(e5);
        element.add(e6);

        adapter = new Adaptateur(getActivity(), element);
        setListAdapter(adapter);
    }

    private void replaceFragment(String frag){
        //Remplace un fragment par un autre
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        switch (frag) {
            case "Scan":
                ft.replace(R.id.linear, new Scan());
                break;
            case "Collection":
                ft.replace(R.id.linear, new Collection());
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

