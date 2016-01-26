package tta.ehu.eus.apptta.Comunicaciones;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class RestClient {
    private final static String AUTH = "Authorization";
    private final String baseUrl;
    private final Map<String, String> properties = new HashMap<>();



    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    private HttpURLConnection getConnection(String path) throws IOException {
        URL url = null;
        if (path.contains("googleapis")) {
            url = new URL(path);
        } else {
            url = new URL(String.format("%s/%s", baseUrl, path));
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        for (Map.Entry<String, String> property : properties.entrySet())
            conn.setRequestProperty(property.getKey(), property.getValue());
        conn.setUseCaches(false);
        return conn;
    }

    public String getString(String path) throws IOException {
        HttpURLConnection conn = null;
        String json = "";
        try {
            conn = getConnection(path);
            final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String linea;
            while ((linea = br.readLine()) != null) {
                json+=linea;
            }

        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return json;
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
