package tta.ehu.eus.apptta.Presentador.Activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import tta.ehu.eus.apptta.Modelo.Coordenadas;
import tta.ehu.eus.apptta.Modelo.Establecimiento;
import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CameraUpdate mCamera;

    public double latitud;
    public double longitud;

    public String query = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        query = intent.getStringExtra(ContentActivity.EXTRA_TYPE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Manipula el mapa una vez esté disponible

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int zoom = 13;
        obtenerLocalizacion();
        LatLng actual = new LatLng(latitud, longitud);

        // Link funcional en navegador (no en la app!)
        // Utiliza la API Key del navegador
        // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=43.1699776,-2.6328183&radius=1000&keyword=panaderia&key=AIzaSyBsRFonYYpWr2R1nxMdtH-Mw-IgSOeyYmk&sensor=true

        final String path = obtenerPathGMaps(latitud, longitud, query);

        final Data data = new Data();

        new Thread() {
            @Override
            public void run() {

                try {
                    final Coordenadas[] coordenadas = data.getCoordenadas(path);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.encontrados) + ": " + String.valueOf(coordenadas.length - 1), Toast.LENGTH_LONG).show();
                        }
                    });

                    for (int i = 0; i < coordenadas.length; i++) {
                        String path2 = obtenerPathGMapsIndividual(coordenadas[i].getPlace_id());
                        final Establecimiento est = data.getDatosEstablecimiento(path2);
                        final LatLng coordenada = new LatLng(coordenadas[i].getLatitud(), coordenadas[i].getLongitud());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions().position(coordenada).title(est.getName()).snippet(est.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
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

        mMap.addMarker(new MarkerOptions().position(actual).title(getResources().getString(R.string.localizacion_actual)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        mCamera = CameraUpdateFactory.newLatLngZoom(actual, zoom);
        mMap.animateCamera(mCamera);
    }

    //Función para obtener la localización actual

    private void obtenerLocalizacion() throws SecurityException {
        LocationManager mLocationManager = null;
        Location myLocation = getLastKnownLocation(mLocationManager);

        if (myLocation == null) {
            Toast.makeText(getApplicationContext(), R.string.sinLocalizacion, Toast.LENGTH_SHORT).show();
            // Coordenadas ETSI Bilbao
            latitud = 43.2624006;
            longitud = -2.9484819;
        } else {
            Toast.makeText(getApplicationContext(), R.string.conLocalizacion, Toast.LENGTH_SHORT).show();
            latitud = myLocation.getLatitude();
            longitud = myLocation.getLongitude();
        }


    }

    //Función para crear el path que ayudará a buscar todos los establecimientos en la zona.

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

    // Función para crear el path, que servirá para realizar una petición
    // por cada establecimiento obtenido en la petición anterior.

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


    //Función que busca la última posición conocida más precisa de la lista de proveedores

    private Location getLastKnownLocation(LocationManager mLocationManager) throws SecurityException {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
