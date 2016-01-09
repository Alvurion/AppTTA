package tta.ehu.eus.apptta.Presentacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tta.ehu.eus.apptta.Comunicaciones.RestClient;
import tta.ehu.eus.apptta.Modelo.Usuario;

/**
 * Created by Julen on 9/1/16.
 */
public class Data {
    RestClient restClient;
    String server="http://192.168.0.24:8080/RefugiApp/rest/AppTTA";

    String pathUser="getUser?name=%s";

    public Data(){
        restClient = new RestClient(server);
    }
    public Usuario getUser(String name, String password) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathUser,name));
        JSONArray a=o.getJSONArray("users");
        JSONObject objeto= a.getJSONObject(0);
        String nombre= objeto.getString("name");
        String pas=objeto.getString("password");
        Usuario u = new Usuario(nombre,pas);
        return u;
    }

    public Boolean esCorrecto(String nameOriginal , String passwordOriginal,String nameRecogido, String passRecogido){
        if (nameOriginal.equals(nameRecogido) && passwordOriginal.equals(passRecogido)){
            return true;
        }
        else{
            return false;
        }
    }
    //Metodos POST
    public int postUser(String name, String pass) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("password", pass);
        return restClient.postJson(jo, "addUser");
    }

}
