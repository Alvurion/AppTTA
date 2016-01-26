package tta.ehu.eus.apptta.Presentacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tta.ehu.eus.apptta.Comunicaciones.RestClient;
import tta.ehu.eus.apptta.Modelo.Coordenadas;
import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Modelo.Usuario;

public class Data {
    RestClient restClient;

    //String server="http://u017633.ehu.eus:18080/RefugiApp/rest/AppTTA";
    //192.168.0.24-- IP DE CASA
    //IP CLASE 10.106.29.222
    String server="http://10.106.25.244:8080/RefugiApp/rest/AppTTA";

    String pathUser="getUsuario?name=%s";
    String pathPhrase="getPhrases?type=%d";
    String pathPhraseUser="getPhrasesUser?idUser=%d";

    public Data(){
        restClient = new RestClient(server);
    }
    public Usuario getUser(String name) throws IOException, JSONException {

        JSONObject o= restClient.getJson(String.format(pathUser, name));
        Usuario usuario=new Usuario();
        usuario.setName(o.getString("name"));
        usuario.setUserId(o.getInt("userId"));
        return usuario;
    }

    public Frase[] getPhrases(int type) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathPhrase, type));
        JSONArray a=o.getJSONArray("phrase");
        Frase[] fraseArray=new Frase[a.length()];
        for(int i=0;i<a.length();i++) {
            JSONObject objeto = a.getJSONObject(i);
            String fraseArabe = objeto.getString("phraseAr");
            String fraseEspanol = objeto.getString("phraseEs");
            String audioFrase = objeto.getString("audioFrase");
            fraseArray[i]=new Frase();
            fraseArray[i].setType(type);
            fraseArray[i].setPhraseEs(fraseEspanol);
            fraseArray[i].setPhraseAr(fraseArabe);
            fraseArray[i].setAudioFrase(audioFrase);
        }
        return fraseArray;
    }
    public Frase[] getPhrasesUser(int userId) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathPhraseUser, userId));
        JSONArray a=o.getJSONArray("content");
        Frase[] fraseArray=new Frase[a.length()];
        for(int i=0;i<a.length();i++) {
            JSONObject objeto = a.getJSONObject(i);
            String fraseArabe = objeto.getString("fraseAr");
            String fraseEspanol = objeto.getString("fraseEs");
            int type=objeto.getInt("type");
            fraseArray[i]=new Frase();
            fraseArray[i].setType(type);
            fraseArray[i].setPhraseEs(fraseEspanol);
            fraseArray[i].setPhraseAr(fraseArabe);
            fraseArray[i].setAudioFrase("buenos_dias.mp3");
        }
        return fraseArray;
    }

    public Boolean esCorrecto(int comprobacion){
        if (comprobacion==200){
            return true;
        }
        else{
            return false;
        }
    }

    public int cogerId(Usuario usuario){
        int id =usuario.getUserId();
        return id;
    }
    //Metodos POST
    public int postUser(String name, String pass) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("password", pass);
        return restClient.postJson(jo, "addUser");
    }

    //Metodos POST
    public int comproUser(String name, String pass) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("password", pass);

        return restClient.postJson(jo, "comprobarUser");
    }

    //Metodos POST
    public int postPhrase(String fraseEs,String fraseAr,int type,int userId) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("fraseEs", fraseEs);
        jo.put("fraseAr", fraseAr);
        jo.put("type", type);
        jo.put("usersId", userId);
        return restClient.postJson(jo, "addPhrase");
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
