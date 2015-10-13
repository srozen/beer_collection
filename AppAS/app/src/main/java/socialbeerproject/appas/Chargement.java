package socialbeerproject.appas;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Rémy on 13-10-15.
 */
public class Chargement {

    private ImageView imageCha = null;
    private boolean stop;
    private Activity act;

    public Chargement(Activity act, RelativeLayout l){
        imageCha = new ImageView(act);
        imageCha.setImageResource(R.mipmap.chargement);

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParams.addRule(RelativeLayout.BELOW, l.getId());
        imageCha.setLayoutParams(relativeParams);

        l.addView(imageCha);
        this.act = act;
    }

    public void start(){
        imageCha.startAnimation(AnimationUtils.loadAnimation(act, R.anim.chargement));
    }

    public void stop(){
        imageCha.clearAnimation();
        stop=true;
    }
}
