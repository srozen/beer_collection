package socialbeerproject.appas.Serveur;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Divers.Chargement;

/**
 * Created by Rémy on 13-10-15.
 * Classe gérant les communications avec l'API serveur
 */
public class ServeurCom {

    private RelativeLayout chargementRel;
    private ActivityCom act;

    public ServeurCom(RelativeLayout r, ActivityCom act){
        this.chargementRel = r;
        this.act = act;
    }

    public void receptionRep(JSONObject rep){
        if (act!=null){
            Chargement.getInstance().stop();
            Chargement.getInstance().dettach();
            act.communication(rep);
        } else {
            Chargement.getInstance().stop();
            Chargement.getInstance().dettach();
        }
    }

    public void connexion(String username, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("connexion", "connexion"));

        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("login", username));

        this.envoieServeur(params);
    }

    public void inscription(String username, String password, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("inscription", "inscription"));

        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("login", username));

        this.envoieServeur(params);
    }

    public void catalogue(String userId, String tri){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("catalogue", "catalogue"));

        params.add(new BasicNameValuePair("userId", userId));

        this.envoieServeur(params);
    }

    public void collection(String userId, String tri){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("collection", "collection"));

        params.add(new BasicNameValuePair("userId", userId));

        this.envoieServeur(params);
    }

    private void envoieServeur(List<NameValuePair> params){
        Chargement.getInstance().attach((Activity) chargementRel.getContext(), chargementRel);
        Chargement.getInstance().start();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new DemandeHTTP(this).execute(params);
    }
}
