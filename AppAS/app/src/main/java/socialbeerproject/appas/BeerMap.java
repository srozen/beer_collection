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

<<<<<<< HEAD

    JSONObject AllBarsShops;
    JSONObject AllShops;
    JSONObject AllBars;
=======
    JSONArray AllShops;
    JSONArray AllBars;
>>>>>>> e2630f2fa19af194903f56e48bfb6585e13c61b4


    @Override
    public void communication(JSONObject rep) {
<<<<<<< HEAD
        if(rep != null && rep.has("AllBS")){
            AllBarsShops = rep;
            compteur++;
        } else if(rep != null && rep.has("bars")){
            AllBars = rep;
            compteur++;
        } else if(rep != null && rep.has("shops")){
            AllShops = rep;
            compteur++;
=======

         if(rep != null && rep.has("bars")){
            try {
                AllBars = rep.getJSONArray("bars");
                drawMarkers("bars");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(rep != null && rep.has("shops")) {
            try {
                AllShops = rep.getJSONArray("shops");
                drawMarkers("shops");
            } catch (JSONException e) {
                e.printStackTrace();
            }
>>>>>>> e2630f2fa19af194903f56e48bfb6585e13c61b4
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
        ServeurCom ser = new ServeurCom((RelativeLayout) this.findViewById(R.id.rel_map), this);
        ser.map(type);
    }

<<<<<<< HEAD
    private void drawMarkers() {
        /* TODO :

            Pour tout les shop
                si shop
                    setMarkerShopPos (GoogleMap map, String title, String information, String phone LatLng pos) {
                sinon
                    setMarkerBarPos(GoogleMap map, String title, String information, String phone, LatLng pos) {


         */
=======

    private void drawMarkers(String type) {
        int i;
        LatLng wLatLng;


        if (type == "bars") {
            for (i=0;i<AllBars.length();i++) {
                try {
                    Bar workBar = this.creationBar(AllBars.getJSONObject(i));
                    wLatLng = new LatLng(workBar.getLattitude(),workBar.getLongitude());
                    this.setMarkerBarPos(map, workBar.getName(), workBar.contact.toString(), wLatLng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (i=0;i<AllShops.length();i++) {
                try {
                    Shop workShop = this.creationShop(AllShops.getJSONObject(i));
                    wLatLng = new LatLng(workShop.getLattitude(),workShop.getLongitude());
                    this.setMarkerShopPos(map, workShop.getName(), workShop.contact.toString(), wLatLng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

>>>>>>> e2630f2fa19af194903f56e48bfb6585e13c61b4
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.setUserPos(map);

        this.demandeServeur("Bars");
        this.demandeServeur("Shops");

        /* Premier marker - Bar
        LatLng work = new LatLng(49.612764, 5.655492);
        this.setMarkerBarPos(map,"Le Gaumais", "Rue du Château 32/B, 6747 Saint-Léger", work);

        // Second marker - Bar
        work = new LatLng(49.611124,5.655101);
        this.setMarkerBarPos(map, "Le Caf'Conç", "Rue du Cinq Septembre 31, 6747 Saint-Léger", work);

        // Troisième marker - Shop
        work = new LatLng(49.610636, 5.653723);
        this.setMarkerShopPos(map, "Spar", "Rue du Cinq Septembre 44, 6747 Saint-Léger", work);*/

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

<<<<<<< HEAD
=======
    private BarShop creationBarShop(JSONObject bs) {
        BarShop work;

        String id="";
        String telephone="";
        String website="";
        String street="";
        String number="";
        String zipcode="";
        String city="";
        String country="";
        String place_id="";
        String beer_type="";

        try {
            id = bs.getString("name");
            telephone = bs.getString("telephone");
            website = bs.getString("website");
            street = bs.getString("street");
            number = bs.getString("number");
            city = bs.getString("city");
            country = bs.getString("country");
            place_id = bs.getString("place_id");
            beer_type = bs.getString("beer_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        work = new BarShop(id,telephone,website,street,number,zipcode,city,country,place_id,beer_type);

        return work;

    }

    private Shop creationShop(JSONObject shop) {
        Shop work=null;

        String id="";
        String name="";
        String description=" ";
        double latitude=0;
        double longitude=0;
        BarShop contact;

        try {
            id = shop.getString("id");
            name = shop.getString("name");
          //  description = shop.getString("description");
            latitude = shop.getDouble("latitude");
            longitude = shop.getDouble("longitude");
            contact = this.creationBarShop(shop.getJSONObject("contact"));
            work = new Shop(id,name,description,latitude,longitude,contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return work;

    }

    private Bar creationBar(JSONObject bar) {
        Bar work = null;

        String id="";
        String name="";
        String description=" ";
        double latitude=0;
        double longitude=0;
        BarShop contact;

        try {
            id = bar.getString("id");
            name = bar.getString("name");
            //description = bar.getString("description");
            latitude = bar.getDouble("latitude");
            longitude = bar.getDouble("longitude");
            contact = this.creationBarShop(bar.getJSONObject("contact"));
            work = new Bar(id,name,description,latitude,longitude,contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return work;
    }

>>>>>>> e2630f2fa19af194903f56e48bfb6585e13c61b4


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