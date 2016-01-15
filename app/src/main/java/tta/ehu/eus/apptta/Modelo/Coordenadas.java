package tta.ehu.eus.apptta.Modelo;

public class Coordenadas {
    double latitud;
    double longitud;

    public Coordenadas(double latitud, double longitud){
        this.latitud=latitud;
        this.longitud=longitud;
    }

    public Coordenadas(){

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
