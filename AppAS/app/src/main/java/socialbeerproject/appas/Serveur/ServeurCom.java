package socialbeerproject.appas.Serveur;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.ImageView;
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
 * Classe gérant les communications avec l'API serveur (et le chargement)
 */
public class ServeurCom {

    private RelativeLayout chargementRel;
    private ActivityCom act;

    public ServeurCom(RelativeLayout r, ActivityCom act){
        this.chargementRel = r;
        this.act = act;
    }

    /**
     * Fonction qui receptionnera la réponse du serveur, et arretera le l'animation du chargement)
     * @param rep Réponse du serveur
     */
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

    public void catalogue(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("catalogue", "catalogue"));

        this.envoieServeur(params);
    }

    public void collection(String userId, String tri){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("collection", "collection"));

        params.add(new BasicNameValuePair("userId", userId));

        this.envoieServeur(params);
    }

    public void profilBiere(String idBiere){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("profilBiere", "profilBiere"));

        params.add(new BasicNameValuePair("id", idBiere));
        this.envoieServeur(params);
    }

    public void recuperationImage(String beerId, ImageView imgBout, ImageView imgEtiq){
        String urlBout = ImageHTTP.cheminImageBouteille + beerId +".jpg";
        new ImageHTTP(imgBout).execute(urlBout);
        String urlEtiq = ImageHTTP.cheminImageEtiquette + beerId +".jpg";
        new ImageHTTP(imgEtiq).execute(urlEtiq);
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
