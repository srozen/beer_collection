package socialbeerproject.appas.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import socialbeerproject.appas.Fragments.ListeBiere;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Fragments.MenuP;


public class Principal extends ActivityCom {

    public MenuP men;
    public ListeBiere collection;
    public ListeBiere catalogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //On applique le layout du fragment à l'activité.
        super.setContentView(R.layout.fragment_menu);
        // Création du nouveau fragment à placer dans le layout de l'activité.
        men = new MenuP();
        collection = new ListeBiere();
        // Add the fragment to the 'fragment_container' FrameLayout
        getFragmentManager().beginTransaction().add(R.id.linear, men).commit();
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
                startActivity(i);*/

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void communication(JSONObject rep) {
        collection.creationListe(rep);
    }
}
