package tta.ehu.eus.apptta.Presentacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tta.ehu.eus.apptta.Comunicaciones.RestClient;
import tta.ehu.eus.apptta.Modelo.Coordenadas;
import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Modelo.Usuario;

/**
 * Created by Julen on 9/1/16.
 */
public class Data {
    RestClient restClient;
    String server="http://10.106.25.244:8080/RefugiApp/rest/AppTTA";

    String pathUser="getUser?name=%s";
    String pathPhrase="getPhrases?type=%d";

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

    public Frase[] getPhrases(int type) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathPhrase, type));
        JSONArray a=o.getJSONArray("phrase");
        Frase[] fraseArray=new Frase[a.length()];
        for(int i=0;i<a.length();i++) {
            JSONObject objeto = a.getJSONObject(i);
            String fraseArabe = objeto.getString("phraseAr");
            String fraseEspanol = objeto.getString("phraseEs");
            int userID = objeto.getInt("userId");
            String audioFrase = objeto.getString("audioFrase");
            fraseArray[i]=new Frase();
            fraseArray[i].setType(type);
            fraseArray[i].setPhraseEs(fraseEspanol);
            fraseArray[i].setPhraseAr(fraseArabe);
            fraseArray[i].setUsersId(userID);
            fraseArray[i].setAudioFrase(audioFrase);
        }
        return fraseArray;
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



















    public Coordenadas[] getCoordenadas (String path) throws IOException,JSONException{

        JSONObject o = restClient.getJson(String.format(path));
        JSONArray results = o.getJSONArray("results");
        Coordenadas[] coordenadas = new Coordenadas[results.length()];

        // Recorrer el array de resultados y obtener de cada uno las coordenadas

        for (int i=0; i<results.length();i++){
            JSONObject objeto = results.getJSONObject(i);
            JSONObject geometry = objeto.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            Double lat = location.getDouble("lat");
            Double lng = location.getDouble("lng");
            coordenadas[i].setLatitud(lat);
            coordenadas[i].setLongitud(lng);
        }

        return coordenadas;
    }

}
