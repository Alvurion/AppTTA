package tta.ehu.eus.apptta.Presentador;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import tta.ehu.eus.apptta.Modelo.Comunicaciones.RestClient;
import tta.ehu.eus.apptta.Modelo.Coordenadas;
import tta.ehu.eus.apptta.Modelo.Establecimiento;
import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Modelo.Usuario;

public class Data {
    RestClient restClient;

    String server = "http://u017633.ehu.eus:18080/RefugiApp/rest/AppTTA";
    String basePlay = "http://u017633.ehu.eus:18080/RefugiApp/file/";
    String pathUser = "getUsuario?name=%s";
    String pathPhrase = "getPhrases?type=%d";
    String pathPhraseUser = "getPhrasesUser?idUser=%d";

    public Data() {
        restClient = new RestClient(server);
    }

    public Usuario getUser(String name) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathUser, name));
        Usuario usuario = new Usuario();
        usuario.setName(o.getString("name"));
        usuario.setUserId(o.getInt("userId"));
        return usuario;
    }

    public Frase[] getPhrases(int type) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathPhrase, type));
        JSONArray a = o.getJSONArray("phrase");
        Frase[] fraseArray = new Frase[a.length()];
        for (int i = 0; i < a.length(); i++) {
            JSONObject objeto = a.getJSONObject(i);
            String fraseArabe = objeto.getString("phraseAr");
            String fraseEspanol = objeto.getString("phraseEs");
            String audioFrase = objeto.getString("audioFrase");
            fraseArray[i] = new Frase();
            fraseArray[i].setType(type);
            fraseArray[i].setPhraseEs(fraseEspanol);
            fraseArray[i].setPhraseAr(fraseArabe);
            fraseArray[i].setAudioFrase(audioFrase);
        }
        return fraseArray;
    }


    public Frase[] getContentUser(int userId) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(pathPhraseUser, userId));
        JSONArray a = o.getJSONArray("content");
        Frase[] fraseArray = new Frase[a.length()];
        for (int i = 0; i < a.length(); i++) {
            JSONObject objeto = a.getJSONObject(i);
            String fraseArabe = objeto.getString("fraseAr");
            String fraseEspanol = objeto.getString("fraseEs");
            String audio = objeto.getString("audio");
            int type = objeto.getInt("type");
            fraseArray[i] = new Frase();
            fraseArray[i].setType(type);
            fraseArray[i].setPhraseEs(fraseEspanol);
            fraseArray[i].setPhraseAr(fraseArabe);
            fraseArray[i].setAudioFrase(audio);
        }
        return fraseArray;
    }

    public Boolean esCorrecto(int comprobacion) {
        if (comprobacion == 200) {
            return true;
        } else {
            return false;
        }
    }


    public int cogerId(Usuario usuario) {
        int id = usuario.getUserId();
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
    public int postPhrase(String fraseEs, String fraseAr, int type, int userId, String audio) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("fraseEs", fraseEs);
        jo.put("fraseAr", fraseAr);
        jo.put("type", type);
        jo.put("usersId", userId);
        jo.put("audio", audio);
        return restClient.postJson(jo, "addPhrase");
    }

    public Coordenadas[] getCoordenadas(String path) throws IOException, JSONException {

        JSONObject o = restClient.getJson(path);
        JSONArray results = o.getJSONArray("results");
        //Coordenadas[] coordenadas = new Coordenadas[results.length()];
        Coordenadas[] coordenadas = new Coordenadas[results.length()];
        // Recorrer el array de resultados y obtener de cada uno las coordenadas

        for (int i = 0; i < results.length(); i++) {
            JSONObject objeto = results.getJSONObject(i);

            JSONObject geometry = objeto.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            String place_id = objeto.getString("place_id");

            coordenadas[i] = new Coordenadas();
            coordenadas[i].setLatitud(lat);
            coordenadas[i].setLongitud(lng);
            coordenadas[i].setPlace_id(place_id);
        }

        return coordenadas;
    }

    public Establecimiento getDatosEstablecimiento(String path) throws IOException, JSONException {

        JSONObject o = restClient.getJson(String.format(path));
        JSONObject result = o.getJSONObject("result");

        Establecimiento est = new Establecimiento(result.getString("name"), result.getString("formatted_address"));

        return est;
    }

    public String getType(int type) {
        String t = null;
        switch (type) {
            case 1:
                t = "Policia";
                break;
            case 2:
                t = "Ayuntamiento";
                break;
            case 3:
                t = "Supermercado";
                break;
            case 4:
                t = "Panadería";
                break;
            case 5:
                t = "Estación";
                break;
            case 6:
                t = "Farmacia";
                break;
            case 7:
                t = "Hospital";
                break;
        }
        return t;
    }

    public String crearAudio(String login, String fraseEsp, String carpetaAudio) {
        final String archivo = fraseEsp.replaceAll("\\s", "") + ".wav";

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dirPathOrigen = absolutePath + "/" +carpetaAudio; //Aqui se encuentran los audios que se graban.

        String dirPathDestinoRefugiApp = absolutePath + "/RefugiApp/";
        File newFileDirectory = new File(dirPathDestinoRefugiApp);

        if (!newFileDirectory.exists()) {
            newFileDirectory.mkdir();
        }

        String dirPathDestinoArchivo = dirPathDestinoRefugiApp + login;
        File newFileArchivo = new File(dirPathDestinoArchivo);

        if (!newFileArchivo.exists()) {
            newFileArchivo.mkdir();
        }

        File dir = new File(dirPathOrigen);
        File[] files = dir.listFiles();

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }

        File newFile = new File(dirPathDestinoArchivo, archivo);
        lastModifiedFile.renameTo(newFile);
        final String pathAudio = dirPathDestinoArchivo + "/" + archivo;

        return pathAudio;
    }


    public Uri getURL(boolean comprobacion, View view) {
        Uri uri;
        if (comprobacion) {
            String url = (String) view.getTag();
            String urlcompleta = basePlay + url;
            uri = Uri.parse(urlcompleta);
        } else {

            String url = (String) view.getTag();
            uri = Uri.parse(url);
        }

        return uri;
    }

}
