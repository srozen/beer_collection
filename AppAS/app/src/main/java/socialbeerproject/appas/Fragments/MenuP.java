package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import be.example.socialbeer.*;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Menu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuP extends ListFragment {
	
    private int Position = 0;
    private ArrayList<String> Item;
    
    Adaptateur adapter;
    private List<elementListe> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        //ex code
        /*
        //Liste des boutons du menu.
        Item = new ArrayList<String>();
        Item.add("Scan");
        Item.add("Collection");

        // On remplit le menu avec notre liste d'options.
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, Item));
                */
        
        element = new ArrayList<elementListe>();

        elementListe e1 = new elementListe("Scan", "scan ta binouze", 1);
        elementListe e2 = new elementListe("Collection", "toute les bières que t'as sifflé", 1);
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

