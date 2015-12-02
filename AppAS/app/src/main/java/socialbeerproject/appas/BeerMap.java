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
import android.content.SharedPreferences;
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
import socialbeerproject.appas.Elements.FriendMap;
import socialbeerproject.appas.Elements.Shop;
import socialbeerproject.appas.Serveur.ServeurCom;


public class BeerMap extends ActivityCom implements OnMapReadyCallback{

    GPSTracker tracker;
    MapFragment mapFragment;
    GoogleMap map;

    JSONArray AllShops;
    JSONArray AllBars;
    JSONArray AllFriends;

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

    @Override
    public void communication(JSONObject rep) {

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
        } else if (rep!= null && rep.has("friends")) {
             try {
                 AllFriends = rep.getJSONArray("friends");
                 drawMarkers("friends");
             } catch (JSONException e) {
                 e.printStackTrace();
             }

         }

    }

    private void demandeServeur(String type){
        ServeurCom ser = new ServeurCom((RelativeLayout) this.findViewById(R.id.rel_map), this);

        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);

        ser.map(type,tracker.getLatitude(),tracker.getLongitude(),log.getString("idUser", "0"));
    }

    private void drawMarkers(String type) {
        int i;
        LatLng wLatLng;


        if (type == "bars") {
            for (i=0;i<AllBars.length();i++) {
                try {
                    Bar workBar = this.creationBar(AllBars.getJSONObject(i));
                    wLatLng = new LatLng(workBar.getLattitude(),workBar.getLongitude());
                    this.setMarkerBarPos(map, workBar.getName(), workBar.contact.getTelephone(), wLatLng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type == "shops") {
            for (i=0;i<AllShops.length();i++) {
                try {
                    Shop workShop = this.creationShop(AllShops.getJSONObject(i));
                    wLatLng = new LatLng(workShop.getLattitude(),workShop.getLongitude());
                    this.setMarkerShopPos(map, workShop.getName(), workShop.contact.getTelephone(), wLatLng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type == "friends") {
            for (i = 0; i < AllFriends.length(); i++) {
                try {
                    FriendMap workFriend = this.creationFriends(AllFriends.getJSONObject(i));

                    if (workFriend.isValid()) {
                        wLatLng = new LatLng(workFriend.getLatitude(), workFriend.getLongitude());

                        int minConvert = workFriend.getDernierCon() / 60;
                        int secConvert = workFriend.getDernierCon() % 60;;

                        this.setMarkerFriendsPos(map, workFriend.getLogin(), "Il y a " + Integer.toString(minConvert) + "m" + Integer.toString(secConvert), wLatLng);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap imap) {
        map = imap;
        this.setUserPos(map);

        this.demandeServeur("Bars");
        this.demandeServeur("Shops");
        this.demandeServeur("Friends");

    }

    public void setUserPos (GoogleMap map) {
        LatLng userPos = new LatLng(tracker.getLatitude(),tracker.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userPos, 15));

        map.addMarker(new MarkerOptions()
                .position(userPos)
                .title("Vous !"));

    }

    public void setMarkerShopPos (GoogleMap map, String title, String information, LatLng pos) {
        map.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_caddy))
                .snippet(information));

    }

    public void setMarkerFriendsPos (GoogleMap map, String title, String information, LatLng pos) {
        map.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_profil))
                .snippet(information));

    }

    public void setMarkerBarPos(GoogleMap map, String title, String information, LatLng pos) {
        map.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_beer))
                .snippet(information));

    }

    private FriendMap creationFriends(JSONObject bs) {
        FriendMap work;

        String id = null;
        boolean valid = false;
        String login = null;
        Double latitude = null;
        Double longitude= null;
        int dernierCon = 0;

        try {
            id = bs.getString("user_id");
            valid = bs.getBoolean("valid");
            login = bs.getString("login");
            latitude = bs.getDouble("latitude");
            longitude = bs.getDouble("longitude");
            dernierCon = bs.getInt("connected");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        work = new FriendMap (id, valid, login, latitude, longitude, dernierCon);

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
            id = bs.getString("id");
            telephone = bs.getString("telephone");
            website = bs.getString("website");
            street = bs.getString("street");
            number = bs.getString("number");
            city = bs.getString("city");
            country = bs.getString("country");
            //place_id = bs.getString("place_id");
            //beer_type = bs.getString("beer_type");
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