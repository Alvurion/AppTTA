package tta.ehu.eus.apptta.Presentador.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import tta.ehu.eus.apptta.Modelo.Comunicaciones.NetworkReceiver;
import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.R;

public class LoginActivity extends AppCompatActivity {

    private NetworkReceiver receiver;
    public final static String EXTRA_LOGIN = "es.tta.ejemplo31.login";
    public final static String EXTRA_PASS = "es.tta.ejemplo31.password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

        EditText editPass = (EditText) findViewById(R.id.TxtPassword);
        EditText editLogin = (EditText) findViewById(R.id.TxtUsuario);
        String log = getLogin();
        String pass = getPassword();
        editPass.setText(pass);

        if (log != null) {
            editLogin.setText(log);
        }

    }

    public void login(final View view) {
        EditText u = (EditText) findViewById(R.id.TxtUsuario);
        final String user = u.getText().toString();
        EditText p = (EditText) findViewById(R.id.TxtPassword);
        final String pass = p.getText().toString();
        CheckBox cb = (CheckBox) findViewById(R.id.box1);

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        } else {

            final Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(EXTRA_LOGIN, user);
            setLogin(u.getText().toString());
            if (cb.isChecked()) {
                setPassword(p.getText().toString());
            }
            final Data data = new Data();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int response;
                    boolean comprobacion = false;

                    try {
                        response = data.comproUser(user, pass);
                        comprobacion = data.esCorrecto(response);

                    } catch (Exception e) {
                        Log.e("ALERTA", e.getMessage(), e);
                    } finally {

                        final boolean finalComprobacion = comprobacion;
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                if (finalComprobacion) {
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.incorrecto, Toast.LENGTH_SHORT).show();
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

    private String getPassword() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getString(EXTRA_PASS, null);
    }

    private void setPassword(String pass) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EXTRA_PASS, pass);
        editor.commit();
    }

}
