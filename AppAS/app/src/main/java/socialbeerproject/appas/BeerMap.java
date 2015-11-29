package socialbeerproject.appas;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import socialbeerproject.appas.Divers.GPSTracker;


public class BeerMap extends FragmentActivity implements OnMapReadyCallback{

    GPSTracker tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tracker = new GPSTracker(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.setUserPos(map);

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
}