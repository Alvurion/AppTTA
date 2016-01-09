package tta.ehu.eus.apptta.Modelo;

/**
 * Created by Julen on 9/1/16.
 */
public class Usuario {

    String name;
    String password;

    public Usuario(String name, String password){
        this.name=name;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
