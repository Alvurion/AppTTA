package tta.ehu.eus.apptta;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tta.ehu.eus.apptta.Modelo.Coordenadas;
import tta.ehu.eus.apptta.Modelo.Establecimiento;
import tta.ehu.eus.apptta.Presentacion.Data;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CameraUpdate mCamera;

    private double latitud;
    private double longitud;

    public String query = null;
    public String placeid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        query = intent.getStringExtra(ContentActivity.EXTRA_TYPE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int zoom = 19;
        obtenerLocalizacion();
        LatLng actual = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(actual).title("Localización actual").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Link funcional
        // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=43.1699776,-2.6328183&radius=1000&keyword=panaderia&key=AIzaSyBsRFonYYpWr2R1nxMdtH-Mw-IgSOeyYmk&sensor=true

        final String path = obtenerPathGMaps(latitud, longitud, query);
        final String path2 = obtenerPathGMapsIndividual(placeid);

        final Data data = new Data();

        new Thread() {
            @Override
            public void run() {

                try {
                    Coordenadas[] coordenadas = data.getCoordenadas(path);

                    for (int i = 0; i < coordenadas.length; i++) {
                        String path2 = obtenerPathGMapsIndividual(coordenadas[i].getPlace_id());
                        final Establecimiento est = data.getDatosEstablecimiento(path2);
                        final LatLng coordenada = new LatLng(coordenadas[i].getLatitud(), coordenadas[i].getLongitud());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions().position(coordenada).title(est.getName()).snippet(est.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        // (FALTA)

        // Hacer un query para obtener las localizaciones cercanas (panaderias, hospitales...).
        // Hay que meter los valores de LAT y LONG que hemos obtenido antes.

        // Aquí se incluirían los diferentes markers para cada resultado obtenido, con addMarker.

        mCamera = CameraUpdateFactory.newLatLngZoom(actual, zoom);
        mMap.animateCamera(mCamera);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
    }


    //Función para obtener la localización actual
    private void obtenerLocalizacion() throws SecurityException {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        latitud = myLocation.getLatitude();
        longitud = myLocation.getLongitude();
    }

    private String obtenerPathGMaps(double latitud, double longitud, String kw) {
        String peticionJson = "https://maps.googleapis.com/maps/api/place/radarsearch/json?";
        String location = "location=";
        String locationValue = String.valueOf(latitud) + "," + String.valueOf(longitud);
        String radius = "radius=";
        String radiusValue = String.valueOf(1000);
        String keyword = "keyword=";
        String keywordValue = kw;
        String key = "key=";
        String keyValue = getResources().getString(R.string.google_maps_key);
        String sensor = "sensor=true";

        String path = peticionJson + location + locationValue
                + "&" + radius + radiusValue
                + "&" + keyword + keywordValue
                + "&" + key + keyValue
                + "&" + sensor;

        return path;

    }

    private String obtenerPathGMapsIndividual(String placeId) {
        String peticionJson = "https://maps.googleapis.com/maps/api/place/details/json?";
        String placeid = "placeid=";
        String placeidValue = placeId;
        String key = "key=";
        String keyValue = getResources().getString(R.string.google_maps_key);

        String path = peticionJson + placeid + placeidValue
                + "&" + key + keyValue;

        return path;

    }
}
