package socialbeerproject.appas.Divers;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import socialbeerproject.appas.R;

/**
 * Created by Rémy on 13-10-15.
 * Singleton pour le chargement (bière qui tourne)
 */
public class Chargement {

    private ImageView imageCha = null;
    private Activity act;
    private RelativeLayout rel;

    /** L'instance statique */
    private static Chargement instance;

    /**
     * Récupère l'instance unique de la class Singleton.
     * Remarque : le constructeur est rendu inaccessible
     */
    public static Chargement getInstance() {
        if (null == instance) { // Premier appel
            instance = new Chargement();
        }
        return instance;
    }

    private Chargement(){

    }

    /**
     * créé et attache l'image à l'activité
     * @param act Activité
     * @param rel RelativeLayout qui affichera la bière qui tourne
     */
    public void attach(Activity act, RelativeLayout rel){
        if (imageCha == null){
            imageCha = new ImageView(act);
            imageCha.setImageResource(R.mipmap.chargement);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.BELOW, rel.getId());
            relativeParams.setMargins(0, 50, 0, 0);
            imageCha.setLayoutParams(relativeParams);

            rel.addView(imageCha);
            this.act = act;
            this.rel = rel;
        }
    }

    /**
     * commence l'animation
     */
    public void start(){
        if (imageCha != null) {
            imageCha.startAnimation(AnimationUtils.loadAnimation(act, R.anim.chargement));
        }
    }

    /**
     * termine l'animation
     */
    public void stop(){
        if (imageCha != null) {
            imageCha.clearAnimation();
        }
    }

    /**
     * Déttache l'image
     */
    public void dettach(){
        if (imageCha != null) {
            rel.removeView(imageCha);
            imageCha.destroyDrawingCache();
            imageCha = null;
            act = null;
        }
    }
}
