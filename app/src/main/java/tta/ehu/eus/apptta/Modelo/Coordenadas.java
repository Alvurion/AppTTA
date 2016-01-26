package tta.ehu.eus.apptta.Modelo;

public class Coordenadas {
    double latitud;
    double longitud;
    String place_id;


    public Coordenadas(double latitud, double longitud, String place_id) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.place_id = place_id;
    }

    public Coordenadas() {

    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
