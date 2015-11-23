package socialbeerproject.appas.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import socialbeerproject.appas.Activity.Login;
import socialbeerproject.appas.Activity.Principal;
import socialbeerproject.appas.Activity.Profil;
import socialbeerproject.appas.Activity.Scan;
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

    @Override
    public void onResume(){
        super.onResume();

        TextView title = (TextView) getActivity().findViewById(R.id.titre_principal);
        title.setText("Menu");

        ImageButton imgButton = (ImageButton) getActivity().findViewById(R.id.btn_principal);
        imgButton.setVisibility(View.GONE);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void selectItem(int index, View v) {
        Position = index;

        getListView().setItemChecked(index, true);
        Principal prin = (Principal) getActivity();

        switch (index) {
            case 0:
                startActivity(new Intent(getActivity(), Scan.class));
                break;
            case 1:
                prin.replaceFragment("Collection");
                break;
            case 2:
                prin.replaceFragment("Catalogue");
                break;
            case 3:
                startActivity(new Intent(getActivity(), Profil.class));
                break;
            case 4:
                prin.replaceFragment("Amitie");
                break;
            case 5:
                break;
            case 6:
                prin.replaceFragment("BonPlan");
                break;
            case 7:
                this.logOut();
                break;
        }
    }

    private void creationMenu(){
        element = new ArrayList<ElementMenuP>();

        ElementMenuP e1 = new ElementMenuP("Scan", "Scan ta binouze", R.mipmap.ic_scan);
        ElementMenuP e2 = new ElementMenuP("Collection", "Toute les bières que tu as sifflé", R.mipmap.ic_collection);
        ElementMenuP e3 = new ElementMenuP("Catalogue", "Liste de tous les bières", R.mipmap.ic_catalogue);
        ElementMenuP e4 = new ElementMenuP("Profil", "Paramètres personnels", R.mipmap.ic_profil);
        ElementMenuP e5 = new ElementMenuP("Amis", "Voir vos amis", R.mipmap.ic_amis);
        ElementMenuP e6 = new ElementMenuP("BeerMap", "Carte des buveurs", R.mipmap.ic_map);
        ElementMenuP e7 = new ElementMenuP("Bons Plans", "Promo sur les bières", R.mipmap.ic_bon_plan);
        ElementMenuP e8 = new ElementMenuP("Déconnexion", "Changement de compte?", R.mipmap.ic_deco);

        element.add(e1);
        element.add(e2);
        element.add(e3);
        element.add(e4);
        element.add(e5);
        element.add(e6);
        element.add(e7);
        element.add(e8);

        adapter = new AdaptateurMenuP(getActivity(), element);
        setListAdapter(adapter);
    }



    private void logOut(){
        SharedPreferences log = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = log.edit();
        edit.remove("username");
        edit.remove("hash");
        edit.remove("idUser");
        edit.apply();
        startActivity(new Intent(getActivity(), Login.class));
        getActivity().finish();
    }
}

