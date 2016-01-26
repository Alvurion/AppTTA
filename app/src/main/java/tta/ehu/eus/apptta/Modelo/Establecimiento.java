package tta.ehu.eus.apptta.Modelo;

public class Establecimiento {
    String name;
    String address;

    public Establecimiento(String name, String address){
        this.name=name;
        this.address=address;
    }

    public Establecimiento(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
