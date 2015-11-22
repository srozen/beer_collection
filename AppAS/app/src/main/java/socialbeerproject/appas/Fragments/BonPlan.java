package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;


import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import socialbeerproject.appas.Elements.ElementPlan;
import socialbeerproject.appas.Adaptateurs.AdaptateurPlan;

public class BonPlan extends ListFragment{

    private int Position = 0;

    private View previous;

    AdaptateurPlan adapter;
    private List<ElementPlan> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        previous = new View(getActivity().getApplicationContext());

        element = new ArrayList<ElementPlan>();

        ElementPlan e1 = new ElementPlan("BeerBar", "2 bières pour le prix d'une","22/11/15","23/11/15","X96ZF");
        ElementPlan e2 = new ElementPlan("Oasis", "Chicha gratuit à l'achat d'une bière", "22/11/15","23/11/15", "AF65R");
        element.add(e1);
        element.add(e2);

        adapter = new AdaptateurPlan(getActivity(), element);
        setListAdapter(adapter);
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
    }



}


