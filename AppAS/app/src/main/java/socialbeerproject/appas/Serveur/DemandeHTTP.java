package socialbeerproject.appas.Serveur;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.List;

/**
 * Created by Rémy on 14-10-15.
 */
public class DemandeHTTP extends AsyncTask<List<NameValuePair>, Integer,JSONObject> {

    public static String url = "https://www.beercollection.be";
    public static String cheminLogin = "api_login.json";
    public static String cheminInsc = "api_register.json";
    public static String cheminCata = "api_catalogue.json";
    public static String cheminColl = "api_collection.json";
    public static String cheminProfilBeer = "api_beer_profile.json";
    public static String cheminAddBeer="api_add_beer.json";
    public static String cheminDeleteBeer="api_delete_beer.json";
    public static String cheminProfil = "api_user_profile.json";
    public static String cheminListeAmi ="api_friendlist.json";
    public static String cheminBonPLan ="api_deals.json";

    public static String cheminAllBeerShop ="api_allshop.json";
    public static String cheminShop ="api_shops.json";
    public static String cheminBar = "api_bars.json";

    private ServeurCom ser;

    public DemandeHTTP(ServeurCom ser){
        super();
        this.ser = ser;
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

        JSONObject jObj;

        // Créé une demande HTTP au serveur avec les paramètres en post
        //DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpClient httpClient = getNewHttpClient();
        HttpPost httpPost = new HttpPost(newUrl);
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF_8"));
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
            case "profil":
                newUrl += cheminProfil;
                break;
            case "listeAmi":
                newUrl += cheminListeAmi;
                break;
            case "bonPlan" :
                newUrl += cheminBonPLan;
                break;
            case "AllBS:" :
                newUrl += cheminAllBeerShop;
                break;
            case "Bars" :
                newUrl += cheminBar;
                break;
            case "Shops":
                newUrl += cheminShop;
                break;
            default:
                return null;
        }
        return newUrl;
    }

    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}