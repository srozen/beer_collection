package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import socialbeerproject.appas.Adaptateurs.Adaptateur;
import socialbeerproject.appas.Elements.ElementListe;
import socialbeerproject.appas.R;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class MenuP extends ListFragment {

    private int Position = 0;
    private ArrayList<String> Item;

    Adaptateur adapter;
    private List<ElementListe> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        element = new ArrayList<ElementListe>();

        ElementListe e1 = new ElementListe("Scan", "scan ta binouze", R.string.icon);
        ElementListe e2 = new ElementListe("Collection", "toute les bières que t'as sifflé", R.string.icon);
        element.add(e1);
        element.add(e2);

        adapter = new Adaptateur(getActivity(), element);
        setListAdapter(adapter);
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
                ft.replace(R.id.linear, new Scan());
                break;
            case 1:
                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                ft.replace(R.id.linear, new Collection());
                break;
        }
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}

