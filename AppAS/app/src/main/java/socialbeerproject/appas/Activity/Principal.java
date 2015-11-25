package socialbeerproject.appas.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Fragments.BonPlan;
import socialbeerproject.appas.Fragments.ListeAmitie;
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
    public BonPlan bonPlan;
    public ListeAmitie amitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On applique le layout du fragment à l'activité.
        super.setContentView(R.layout.fragment_menu);

        // Création du nouveau fragment à placer dans le layout de l'activité.
        men = new MenuP();
        makeCatalogue();
        makeCollection();
        bonPlan = new BonPlan();
        amitie = new ListeAmitie();

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
            if (frag != null && frag.getClass().equals(ListeBiere.class)){
                ListeBiere lb = (ListeBiere) frag;
                lb.creationListe(rep);
            } else if (frag!= null && frag.getClass().equals(ListeAmitie.class)){
                ListeAmitie lb = (ListeAmitie) frag;
                lb.creationListe(rep);
            } else if (frag!= null && frag.getClass().equals(BonPlan.class)) {
                BonPlan bp = (BonPlan) frag;
                bp.creationListe(rep);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void closeFrag(ListFragment lF){
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void replaceFragment(String frag){
        //Remplace un fragment par un autre
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        switch (frag) {
            case "Collection":
                ft.replace(R.id.rel_menu, this.collection);
                break;
            case "Catalogue":
                ft.replace(R.id.rel_menu, this.catalogue);
                break;
            case "BonPlan" :
                ft.replace(R.id.rel_menu, this.bonPlan);
                break;
            case "Amitie" :
                ft.replace(R.id.rel_menu, this.amitie);
                break;
            case "Menu" :
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                ft.replace(R.id.rel_menu, this.men);
                break;
        }

        ft.addToBackStack(null);
        ft.commit();
    }
}
