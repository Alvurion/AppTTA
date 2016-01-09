package tta.ehu.eus.apptta;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tta.ehu.eus.apptta.Comunicaciones.NetworkReceiver;
import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentacion.Data;

public class LoginActivity extends AppCompatActivity {

    private NetworkReceiver receiver;
    public final static String EXTRA_LOGIN = "es.tta.ejemplo31.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Register BroadcastReceiver to track network connection changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

        EditText editLogin = (EditText) findViewById(R.id.TxtUsuario);
        String log = getLogin();
        if (log != null) {
            editLogin.setText(log);
        }

    }

    public void login(final View view) {
        EditText u=(EditText) findViewById(R.id.TxtUsuario);
        final String user=u.getText().toString();
        EditText p=(EditText) findViewById(R.id.TxtPassword);
        final String pass=p.getText().toString();

        //Los tenemos cogidos para cuando tengamos la DB preparada
        if(user.isEmpty() ||pass.isEmpty() ){
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        }
        else{
            //Aqui hacer hilo mientras comprueba que es user correcto o no
            //dar acceso al menu
            final Intent intent = new Intent(this, MenuActivity.class);
            setLogin(u.getText().toString());
            final Data data = new Data();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Usuario usuario = null;
                    Boolean comprobacion= false;
                    try {
                        usuario = data.getUser(user, pass);
                        comprobacion=data.esCorrecto(usuario.getName(),usuario.getPassword(),user,pass);
                    } catch (Exception e) {
                        Log.e("ALERTA", e.getMessage(), e);
                    } finally {
                        final Boolean finalComprobacion = comprobacion;
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                if (finalComprobacion){
                                startActivity(intent);}
                                else{
                                    Toast.makeText(getApplicationContext(),R.string.incorrecto,Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            }).start();
        }

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unregisters BroadcastReceiver when app is destroyed
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    private String getLogin() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getString(EXTRA_LOGIN, null);
    }
    private void setLogin(String login) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EXTRA_LOGIN, login);
        editor.commit();
    }

}
