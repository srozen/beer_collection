package socialbeerproject.appas.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Fragments.ListeBiere;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Fragments.MenuP;

/**
 * Activité du menu principal avec les listes de bière (et scan)
 */
public class Principal extends ActivityCom {

    public MenuP men;
    public ListeBiere collection;
    public ListeBiere catalogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On applique le layout du fragment à l'activité.
        super.setContentView(R.layout.fragment_menu);

        // Création du nouveau fragment à placer dans le layout de l'activité.
        men = new MenuP();
        makeCatalogue();
        makeCollection();

        // Ajout du fragment du menu principal
        getFragmentManager().beginTransaction().add(R.id.rel_menu, men).commit();
    }

    public ListeBiere makeCatalogue(){
        catalogue = new ListeBiere();
        Bundle args = new Bundle();
        args.putString("type", "catalogue");
        catalogue.setArguments(args);
        return catalogue;
    }

    public ListeBiere makeCollection(){
        collection = new ListeBiere();
        Bundle args = new Bundle();
        args.putString("type", "collection");
        collection.setArguments(args);
        return collection;
    }

    @Override
    public void communication(JSONObject rep) {
        try {
            Fragment frag = getFragmentManager().findFragmentById(R.id.rel_menu);
            if (frag.getClass().equals(ListeBiere.class)){
                ListeBiere lb = (ListeBiere) frag;
                lb.creationListe(rep);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
