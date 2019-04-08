package esprit.tn.jobchain;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class RecomMap extends Fragment implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    public Double latMarker;
    public Double longMarker;
    public String nom;
    public String num;
    private LatLngBounds mMapBoundary;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root=inflater.inflate(R.layout.fragment_recom_map, container, false);

        SharedPreferences Location;
        Location = getContext().getSharedPreferences("MarkerRecom", Context.MODE_PRIVATE); //1
        String latrecom = Location.getString("latrecom", null);
        String lagrecom = Location.getString("lagrecom", null);
         nom = Location.getString("nom", null);
         num = Location.getString("num", null);

         latMarker = Double.parseDouble(latrecom);
         longMarker = Double.parseDouble(lagrecom);

        mMapView = root.findViewById(R.id.user_list_map);
        initGoogleMap(savedInstanceState);

        return root;

    }

    void addMarker(double lat , double lang){

        Marker currentMarker = null;

        currentMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lang)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


    }

    private void setCameraView() {



        // Set a boundary to start

        double bottomBoundary = latMarker - .01;

        double leftBoundary = longMarker - .01;

        double topBoundary = latMarker + .01;

        double rightBoundary = longMarker + .01;



        mMapBoundary = new LatLngBounds(

                new LatLng(bottomBoundary, leftBoundary),

                new LatLng(topBoundary, rightBoundary)

        );



        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));

    }

    private void initGoogleMap(Bundle savedInstanceState){

        // *** IMPORTANT ***

        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK

        // objects or sub-Bundles.

        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {

            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);

        }



        mMapView.onCreate(mapViewBundle);



        mMapView.getMapAsync(this);

    }
    @Override

    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);



        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);

        if (mapViewBundle == null) {

            mapViewBundle = new Bundle();

            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);

        }



        mMapView.onSaveInstanceState(mapViewBundle);

    }
    @Override

    public void onResume() {

        super.onResume();

        mMapView.onResume();

    }



    @Override

    public void onStart() {

        super.onStart();

        mMapView.onStart();

    }



    @Override

    public void onStop() {

        super.onStop();

        mMapView.onStop();

    }



    @Override

    public void onMapReady(GoogleMap map) {

        LatLng sydney = new LatLng(latMarker, longMarker);
        map.addMarker(new MarkerOptions().position(sydney)
                .title(nom)
                .snippet(num));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)

                != PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED) {

            return;

        }

        map.setMyLocationEnabled(true);
        mGoogleMap = map;

        setCameraView();

    }



    @Override

    public void onPause() {

        mMapView.onPause();

        super.onPause();

    }



    @Override

    public void onDestroy() {

        mMapView.onDestroy();

        super.onDestroy();

    }



    @Override

    public void onLowMemory() {

        super.onLowMemory();

        mMapView.onLowMemory();

    }

}
