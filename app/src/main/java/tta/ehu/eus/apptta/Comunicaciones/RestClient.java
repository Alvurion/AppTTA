package tta.ehu.eus.apptta.Comunicaciones;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RestClient {
    private final static String AUTH = "Authorization";
    private final String baseUrl;
    private final Map<String, String> properties = new HashMap<>();

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setHttpBasicAuth(String user, String passwd) {
        String basicAuth = Base64.encodeToString(String.format("%s:%s",user,passwd).getBytes(), Base64.DEFAULT);
        setAuthorization(String.format("Basic %s", basicAuth));
    }
    public String getAuthorization() {
        return properties.get(AUTH);
    }
    public void setAuthorization(String auth) {
        properties.put(AUTH, auth);
    }
    public void setProperty(String name, String value) {
        properties.put(name, value);
    }
    private HttpURLConnection getConnection(String path) throws IOException {
        URL url = new URL(String.format("%s/%s", baseUrl, path));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        for (Map.Entry<String, String> property : properties.entrySet())
            conn.setRequestProperty(property.getKey(), property.getValue());
        conn.setUseCaches(false);
        //conn.setRequestProperty("Connection", "Keep-Alive");
        return conn;
    }
    public String getString(String path) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = getConnection(path);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                return br.readLine();
            }
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public JSONObject getJson(String path) throws IOException, JSONException {
        return new JSONObject(getString(path));
    }


    public int postJson(final JSONObject json, String path) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json.toString());
            pw.close();
            return conn.getResponseCode();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
