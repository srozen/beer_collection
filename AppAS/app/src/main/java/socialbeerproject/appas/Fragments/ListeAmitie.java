package socialbeerproject.appas.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Activity.Principal;
import socialbeerproject.appas.Activity.Profil_ami;
import socialbeerproject.appas.Adaptateurs.AdaptateurAmitie;
import socialbeerproject.appas.Elements.ElementAmitie;
import socialbeerproject.appas.Elements.ElementPlan;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ServeurCom;

public class ListeAmitie extends ListFragment {

    private int Position = 0;
    private ArrayList<String> Item;

    private View previous;

    AdaptateurAmitie adapter;
    private List<ElementAmitie> element;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        element = new ArrayList<ElementAmitie>();

        ElementAmitie e1 = new ElementAmitie("Cyril", R.mipmap.ic_launcher, "1");
        ElementAmitie e2 = new ElementAmitie("Cyril2", R.mipmap.ic_launcher, "2");
        element.add(e1);
        element.add(e2);

        adapter = new AdaptateurAmitie(getActivity(), element);
        setListAdapter(adapter);

      /*  ServeurCom ser = new ServeurCom((RelativeLayout) getActivity().findViewById(R.id.rel_menu),(ActivityCom) getActivity());
        ser.amitie();*/

        previous = new View(getActivity().getApplicationContext());
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView title = (TextView) getActivity().findViewById(R.id.titre_principal);
        title.setText("Vos ami(e)s");
        ImageButton imgButton = (ImageButton) getActivity().findViewById(R.id.btn_principal);
        imgButton.setVisibility(View.VISIBLE);
        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Principal prin = (Principal) getActivity();
                prin.replaceFragment("Menu");
                prin.closeFrag(prin.amitie);
            }
        });
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

        Intent intent = new Intent(getActivity(), Profil_ami.class);

        //Avec en paramètre l'id de la vue de l'activité mère et un fragment.
        intent.putExtra("id", element.get(index).getId());
        startActivity(intent);
        getActivity().overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
    }

}