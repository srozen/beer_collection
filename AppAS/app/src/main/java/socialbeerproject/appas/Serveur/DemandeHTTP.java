package socialbeerproject.appas.Serveur;

import android.os.AsyncTask;
import android.os.SystemClock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Rémy on 14-10-15.
 * Tirer en partie d'un forum : http://stackoverflow.com/questions/23945810/json-keep-getting-the-old-data
 */
public class DemandeHTTP extends AsyncTask<List<NameValuePair>, Integer,JSONObject> {

    public static String url = "http://46.101.143.168/mlogin";
    public static String method = "POST";

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
            ser.receptionRep(null);
        }
    }

    @Override
    protected void onPostExecute(JSONObject res){
        if (ser !=null){
            ser.receptionRep(res);
        }
    }

    public DemandeHTTP() {
        super();
    }

    private JSONObject makeHttpRequest(List<NameValuePair> params) throws IOException {
        SystemClock.sleep(2000);
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        // Créé une demande HTTP au serveur avec les paramètres en post
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse httpResponse = httpClient.execute(httpPost);

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        json = sb.toString();

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
}