package socialbeerproject.appas.Fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

        // We can display everything in-place with fragments, so update
        // the list to highlight the selected item and show the data.
        getListView().setItemChecked(index, true);

        //Remplace un fragment par un autre
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (index) {
            case 0:
                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(R.id.linear, new Scan());
                break;
            case 1:
                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(R.id.linear, new Collection());
                break;
            case 2:

                break;
            case 3:
                startActivity(new Intent(getActivity(), Profil.class));
                break;
            case 4:
                break;
            case 5:
                // Détruire préférence ensuite Go Act Login
                break;
        }
            
        ft.addToBackStack(null);
        ft.commit();
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

}

