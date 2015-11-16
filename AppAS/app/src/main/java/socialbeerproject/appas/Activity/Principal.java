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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.item_profil):
                Intent i = new Intent(this, Profil.class);
                startActivity(i);
                break;
            case (R.id.item_menu):
                Intent j = new Intent(this, Principal.class);
                startActivity(j);
            case (R.id.item_info):
            /*    Intent i= new Intent(this,About.class);
                startActivity(i); TODO */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void communication(JSONObject rep) {
        try {
            Fragment frag = getFragmentManager().findFragmentById(R.id.rel_menu);
            if (frag.isVisible()){
                ListeBiere lb = (ListeBiere) frag;
                lb.creationListe(rep);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
