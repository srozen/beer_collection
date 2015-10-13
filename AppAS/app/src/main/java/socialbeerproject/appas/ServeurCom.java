package socialbeerproject.appas;

import android.os.StrictMode;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rémy on 13-10-15.
 * Classe gérant les communications avec l'API serveur
 */
public class ServeurCom {

    public static String url = "http://46.101.143.168/mlogin";

    public static String connexion(String username, String password){
        return ServeurCom.envoieServeur();
    }

    public static boolean inscription(String username, String password, String email){

        return true;
    }

    public static String envoieServeur(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("password", "usr"));
        params.add(new BasicNameValuePair("login", "user"));
        try {
            JSONParser t = new JSONParser();
            JSONObject obj = t.makeHttpRequest(url,"GET",params);
            try {
                return obj.getString("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Bug";
    }
}
