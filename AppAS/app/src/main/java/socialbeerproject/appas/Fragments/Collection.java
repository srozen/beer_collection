package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import socialbeerproject.appas.Adaptateurs.AdaptateurCollection;
import socialbeerproject.appas.Elements.ElementCollection;
import socialbeerproject.appas.R;


public class Collection extends ListFragment {

    private int Position = 0;
    private ArrayList<String> Item;

    AdaptateurCollection adapter;
    private List<ElementCollection> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        element = new ArrayList<ElementCollection>();

        ElementCollection e1 = new ElementCollection("Leffe", R.string.icon);
        ElementCollection e2 = new ElementCollection("CaraPils", R.string.icon);
        element.add(e1);
        element.add(e2);

        adapter = new AdaptateurCollection(getActivity(), element);
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
        //FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (index) {
            case 0:
                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                //ft.replace(R.id.linear, new Scan());
                break;
            case 1:
                //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
                //ft.replace(R.id.linear, new Collection());
                break;
        }

        //ft.addToBackStack(null);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //ft.commit();
    }

}