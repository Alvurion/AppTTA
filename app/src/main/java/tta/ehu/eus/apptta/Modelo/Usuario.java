package tta.ehu.eus.apptta.Modelo;

public class Usuario {

    String name;
    String password;
    int userId;

    public Usuario(String name, String password, int userId){
        this.name=name;
        this.password=password;
        this.userId=userId;
    }
    public Usuario(int userId){
        this.userId=userId;
    }
    public Usuario(){

    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
