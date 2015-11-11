package socialbeerproject.appas.Serveur;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Rémy on 14-10-15.
 */
public class DemandeHTTP extends AsyncTask<List<NameValuePair>, Integer,JSONObject> {

    public static String url = "http://46.101.143.168";
    public static String cheminLogin = "api_login.json";
    public static String cheminInsc = "api_register.json";
    public static String cheminCata = "api_catalogue.json";
    public static String cheminColl = "api_collection.json";
    public static String cheminProfilBeer = "api_beer_profile.json";
    public static String cheminAddBeer="api_add_beer.json";
    public static String cheminDeleteBeer="api_delete_beer.json";

    private ServeurCom ser;
    private boolean con;

    public DemandeHTTP(ServeurCom ser){
        super();
        this.ser = ser;
    }

    public DemandeHTTP(ServeurCom ser, boolean con){
        super();
        this.ser = ser;
        this.con = con;
    }

    @Override
    protected JSONObject doInBackground(List<NameValuePair>... params) {
        try {
            return this.makeHttpRequest(params[0]);
        } catch (IOException e) {
            cancel(true);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCancelled(){
        if (ser !=null){
            try {
                ser.receptionRep(null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject res){
        if (ser !=null){
            try {
                ser.receptionRep(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envoie une requête à l'api correspondant et attend la réponse (à utiliser de manière
     * asynchrone!)
     * @param params liste de paramatre qui seront introduit dans la demande
     * @return retourne la réponse du serveur dans un fichier JSON
     * @throws IOException
     */
    private JSONObject makeHttpRequest(List<NameValuePair> params) throws IOException {

        String newUrl = getNewUrl(params.get(0).getName());
        params.remove(0);

        JSONObject jObj = null;

        // Créé une demande HTTP au serveur avec les paramètres en post
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(newUrl);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse httpResponse = httpClient.execute(httpPost);

        String json = this.lecture(httpResponse);

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            cancel(true);
            e.printStackTrace();
            return null;
        }

        // return JSONObject
        return jObj;
    }

    private String lecture(HttpResponse httpResponse) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    /**
     * Défini l'url de l'Api conrespondant
     * @param namCase nom de la demande. (connexion, ...)
     * @return Retourne le nouvel URL
     */
    private String getNewUrl(String namCase){
        String newUrl= new String(url+'/');

        switch (namCase){
            case "inscription" :
                newUrl += cheminInsc;
                break;
            case "connexion" :
                newUrl += cheminLogin;
                break;
            case "catalogue" :
                newUrl += cheminCata;
                break;
            case "collection" :
                newUrl += cheminColl;
                break;
            case "profilBiere" :
                newUrl += cheminProfilBeer;
                break;
            case "ajoutColl":
                newUrl += cheminAddBeer;
                break;
            case "deleteColl":
                newUrl += cheminDeleteBeer;
                break;
            default:
                return null;
        }
        return newUrl;
    }
}