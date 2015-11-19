package socialbeerproject.appas.Serveur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static socialbeerproject.appas.Serveur.DemandeHTTP.getNewHttpClient;

/**
 * Created by Rémy on 03-11-15.
 * Tirer en partie d'un forum : http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 * Permet de récuperer une image grâce à un url (asynchrone)
 */
public class ImageHTTP  extends AsyncTask<String, Void, Bitmap> {

    public static String cheminImageBouteille = DemandeHTTP.url + "/images/beer_profile/";
    public static String cheminImageEtiquette = DemandeHTTP.url + "/images/beer_sticker/";
    public static String cheminGravatar = "http://www.gravatar.com/avatar/";

    private ImageView bmImage;

    public ImageHTTP(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream is = (InputStream) new URL(urlDisplay).getContent();
            mIcon11 = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null){
            bmImage.setImageBitmap(result);
        }
    }
}
