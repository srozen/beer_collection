package socialbeerproject.appas;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Divers.GPSTracker;
import socialbeerproject.appas.Serveur.ServeurCom;


public class BeerMap extends ActivityCom implements OnMapReadyCallback{

    GPSTracker tracker;
    MapFragment mapFragment;


    JSONObject AllBarsShops;
    JSONObject AllShops;
    JSONObject AllBars;

    int compteur = 0;

    @Override
    public void communication(JSONObject rep) {
        if(rep != null && rep.has("AllBS")){
            AllBarsShops = rep;
            compteur++;
        } else if(rep != null && rep.has("bars")){
            AllBars = rep;
            compteur++;
        } else if(rep != null && rep.has("shops")){
            AllShops = rep;
            compteur++;
        }

        if (compteur == 3) {
            drawMarkers();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        tracker = new GPSTracker(this);

    }

    private void demandeServeur(String type){
        ServeurCom ser = new ServeurCom((RelativeLayout) this.findViewById(R.id.rel_menu), this);
        ser.map(type);
    }

    private void drawMarkers() {
        /* TODO :

            Pour tout les shop
                si shop
                    setMarkerShopPos (GoogleMap map, String title, String information, String phone LatLng pos) {
                sinon
                    setMarkerBarPos(GoogleMap map, String title, String information, String phone, LatLng pos) {


         */
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.setUserPos(map);

        this.demandeServeur("AllBS");
        this.demandeServeur("Bars");
        this.demandeServeur("Shops");

        // Premier marker - Bar
        LatLng work = new LatLng(49.612764, 5.655492);
        this.setMarkerBarPos(map,"Le Gaumais", "Rue du Château 32/B, 6747 Saint-Léger", work);

        // Second marker - Bar
        work = new LatLng(49.611124,5.655101);
        this.setMarkerBarPos(map, "Le Caf'Conç", "Rue du Cinq Septembre 31, 6747 Saint-Léger", work);

        // Troisième marker - Shop
        work = new LatLng(49.610636, 5.653723);
        this.setMarkerShopPos(map, "Spar", "Rue du Cinq Septembre 44, 6747 Saint-Léger", work);

    }

    public void setMarkerShopPos (GoogleMap map, String title, String information, LatLng pos) {
        map.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_caddy))
                .snippet(information));

    }

    public void setUserPos (GoogleMap map) {
        LatLng userPos = new LatLng(tracker.getLatitude(),tracker.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15));

        map.addMarker(new MarkerOptions()
                .position(userPos)
                .title("Vous !"));

    }

    public void setMarkerBarPos(GoogleMap map, String title, String information, LatLng pos) {
        map.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer))
                .snippet(information));

    }



    /* http://stackoverflow.com/questions/843675/how-do-i-find-out-if-the-gps-of-an-android-device-is-enabled */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setMessage("Votre géolocalisation n'est pas activée, voulez-vous l'activer ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}