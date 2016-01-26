package tta.ehu.eus.apptta;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentacion.Data;

public class PhraseRegisterActivity extends AppCompatActivity {
    public final static String EXTRA_LOGIN="tta.ehu.eus.apptta.EXTRA_LOGIN";
    public static String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_register);
        Intent intent = getIntent();
        login = (String) intent.getSerializableExtra(ContentMyPhrasesActivity.EXTRA_LOGIN);
    }



    public void registerPhrase(final View view) throws IOException, JSONException {
        //Recogemos las variables que queremos comprobar
        EditText f = (EditText) findViewById(R.id.fraseEsp);
        final String fraseEsp = f.getText().toString();
        EditText f1 = (EditText) findViewById(R.id.fraseArb);
        final String fraseArb = f1.getText().toString();
        final int type = 1;
        final Data data = new Data();



        final Intent intent = new Intent(this, ContentMyPhrasesActivity.class);
        intent.putExtra(EXTRA_LOGIN,login);


        if (fraseEsp.isEmpty() || fraseArb.isEmpty()) {
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Integer respuesta = null;
                    try {
                        final Usuario usuario= data.getUser(login);
                        final int usersId =data.cogerId(usuario);
                       respuesta = data.postPhrase(fraseEsp, fraseArb, type, usersId);
                    } catch (Exception e) {
                        Log.e("ALERTA", e.getMessage(), e);
                    } finally {
                        final Integer finalRespuesta = respuesta;
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                if (finalRespuesta == 200) {
                                    Toast.makeText(getApplicationContext(), R.string.exitoFrase, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.registroFallidoFrase, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            }).start();
        }

    }


}


