package socialbeerproject.appas.Serveur;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private String pass;
    private String username;
    private String email;

    public ServeurCom(RelativeLayout r, ActivityCom act){
        this.chargementRel = r;
        this.act = act;
    }

    /**
     * Fonction qui receptionnera la réponse du serveur, et arretera le l'animation du chargement)
     * @param rep Réponse du serveur
     */
    public void receptionRep(JSONObject rep) throws JSONException {
        if (act!=null){
            if(!checkLog(rep)){
                Chargement.getInstance().stop();
                Chargement.getInstance().dettach();
                act.communication(rep);
            }
        } else {
            Chargement.getInstance().stop();
            Chargement.getInstance().dettach();
        }
    }

    /**
     *
     * @param rep
     * @return
     * @throws JSONException
     */
    private boolean checkLog(JSONObject rep) throws JSONException {
        //Pour la connexion
        if (rep!=null && rep.has("exists") ){
            if (rep.getBoolean("exists")){
                String sel = rep.getString("saltUser");
                String idUser = rep.getString("idUser");
                if (sel != null ){
                    String hash = getHash(sel);
                    connexionTwo(hash,idUser);
                    return true;
                }
            } else {
                return false;
            }
        }
        //Pour l'inscription
        if (rep!=null && rep.has("checkMail") && rep.has("checkUser") && rep.has("saltUser")){
            if (rep.getString("checkUser") == "true" && rep.getString("checkMail") == "true"){
                String sel = rep.getString("saltUser");
                if (sel != null ){
                    String hash = getHash(sel);
                    inscriptionTwo(hash,sel);
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Hash le mot de passe avec le sel recu du serveur et le mot de passe
     * @param sel Sel envoyer depuis le serveur (calculé avec le nom du profil)
     * @return Renvoie une chaine de caractère hashée
     */
    private String getHash(String sel){
        sel = sel + "--" + this.pass;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(sel.getBytes());
            byte byteData[] = md.digest();
            //No_WRAP évite au hash d'ajout un \n (contrairement au DEFAULT)
            String base64 = Base64.encodeToString(byteData, Base64.NO_WRAP);
            return base64;
        } catch (NoSuchAlgorithmException e) {
            return "Bug";
        }
    }

    /**
     * Finalise la connexion après le hash. (deuxième étape)
     * @param hash
     * @param idUser
     */
    private void connexionTwo(String hash, String idUser){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("connexion", "connexion"));

        params.add(new BasicNameValuePair("idUser", idUser));
        params.add(new BasicNameValuePair("password", hash));

        this.pass = "";

        this.envoieServeur(params);
    }

    /**
     * Fait une demande de sel au serveur pour la connexion
     * @param username
     * @param password
     */
    public void connexionOne(String username, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("connexion", "connexion"));

        params.add(new BasicNameValuePair("step", "find"));
        params.add(new BasicNameValuePair("login", username));

        this.pass = password;

        this.envoieServeur(params);
    }

    /**
     * Fait une demande de sel pour l'inscription
     * @param hash
     * @param sel
     */
    public void inscriptionTwo(String hash, String sel){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("inscription", "inscription"));

        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("login", username));
        params.add(new BasicNameValuePair("password",hash));
        params.add(new BasicNameValuePair("saltUser",sel));

        this.pass = "";
        this.username = "";
        this.email = "";

        this.envoieServeur(params);
    }

    public void inscriptionOne(String username, String password, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("inscription", "inscription"));

        params.add(new BasicNameValuePair("step", "find"));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("login", username));

        this.pass = password;
        this.username = username;
        this.email = email;

        this.envoieServeur(params);
    }

    public void catalogue(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("catalogue", "catalogue"));

        this.envoieServeur(params);
    }

    public void amitie(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("amitie", "amitie"));



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
