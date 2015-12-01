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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Activity.ActivityCom;
import socialbeerproject.appas.Divers.GPSTracker;
import socialbeerproject.appas.Elements.Bar;
import socialbeerproject.appas.Elements.BarShop;
import socialbeerproject.appas.Elements.Shop;
import socialbeerproject.appas.Serveur.ServeurCom;


public class BeerMap extends ActivityCom implements OnMapReadyCallback{

    GPSTracker tracker;
    MapFragment mapFragment;
    GoogleMap map;

    JSONArray AllBarsShops;
    JSONArray AllShops;
    JSONArray AllBars;

    int compteur = 0;

    @Override
    public void communication(JSONObject rep) {
        if(rep != null && rep.has("AllBS")){
            try {
                AllBarsShops = rep.getJSONArray("ee");
                compteur++;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if(rep != null && rep.has("bars")){
            try {
                AllBars = rep.getJSONArray("ee");
                compteur++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(rep != null && rep.has("shops")) {
            try {
                AllShops = rep.getJSONArray("ee");
                compteur++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        int i;
        BarShop workBarShop = null;
        LatLng wLatLng;

        for (i = 0;i<AllBarsShops.length();i++); {

            try {
                workBarShop = this.creationBarShop(AllBarsShops.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (workBarShop.getBeer_place() == "Shop") {

                Shop workShop = this.findShopInJsonArray( workBarShop.getId());
                wLatLng = new LatLng(workShop.getLattitude(),workShop.getLongitude());
                this.setMarkerShopPos(map, workShop.getName(), workBarShop.toString(), wLatLng);


            } else if (workBarShop.getBeer_place() == "Bar") {

                Bar workBar = this.findBarInJsonArray(workBarShop.getId());
                wLatLng = new LatLng(workBar.getLattitude(),workBar.getLongitude());
                this.setMarkerShopPos(map,workBar.getName(),workBarShop.toString(), wLatLng);

            } else {
                System.out.println("erreur niveau bar shop");
            }
        }
    }



    @Override
    public void onMapReady(GoogleMap imap) {
        map = imap;
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

    private Shop findShopInJsonArray( String id){
        int i = 0;
        int key = -1;
        Shop work = null;

        try {
            while (i < AllShops.length()) {
                work = this.creationShop(AllShops.getJSONObject(i));
                if (work.getId() == id) return work;
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return work;
    }

    private Bar findBarInJsonArray( String id){
        int i = 0;
        int key = -1;
        Bar work = null;

        try {
            while (i < AllBars.length()) {
                work = this.creationBar(AllBars.getJSONObject(i));
                if (work.getId() == id) return work;
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return work;
    }

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
        Shop work;

        String id="";
        String name="";
        String description="";
        double latitude=0;
        double longitude=0;

        try {
            id = shop.getString("id");
            name = shop.getString("name");
            description = shop.getString("description");
            latitude = shop.getDouble("latitude");
            longitude = shop.getDouble("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        work = new Shop(id,name,description,latitude,longitude);

        return work;

    }

    private Bar creationBar(JSONObject bar) {
        Bar work;

        String id="";
        String name="";
        String description="";
        double latitude=0;
        double longitude=0;

        try {
            id = bar.getString("id");
            name = bar.getString("name");
            description = bar.getString("description");
            latitude = bar.getDouble("latitude");
            longitude = bar.getDouble("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        work = new Bar(id,name,description,latitude,longitude);

        return work;

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