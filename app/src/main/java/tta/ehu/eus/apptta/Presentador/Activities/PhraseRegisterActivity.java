package tta.ehu.eus.apptta.Presentador.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.R;

public class PhraseRegisterActivity extends AppCompatActivity {
    public final static String EXTRA_LOGIN = "tta.ehu.eus.apptta.EXTRA_LOGIN";
    public String login;

    MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_register);

        recorder = new MediaRecorder();

        Intent intent = getIntent();
        login = (String) intent.getSerializableExtra(ContentMyPhrasesActivity.EXTRA_LOGIN);

        Button grabar = (Button) findViewById(R.id.botonGrabarAudio);
        grabar.setVisibility(View.VISIBLE);

        Button registrar = (Button) findViewById(R.id.botonRegistrarFrase);
        registrar.setVisibility(View.INVISIBLE);


    }

    public void recordAudio(View view) throws IOException {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            Toast.makeText(getApplicationContext(), R.string.noMicrophone, Toast.LENGTH_SHORT).show();

            Button grabar = (Button) findViewById(R.id.botonGrabarAudio);
            grabar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.nomicrophone, 0, 0, 0);
            grabar.setClickable(false);

            return;
        } else {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(this, "no tienes app para grabar", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void registerPhrase(final View view) throws IOException, JSONException {
        //Recogemos las variables que queremos comprobar
        EditText f = (EditText) findViewById(R.id.fraseEsp);
        EditText f1 = (EditText) findViewById(R.id.fraseArb);

        final String fraseEsp = f.getText().toString();
        final String fraseArb = f1.getText().toString();
        final int type = 1;
        final Data data = new Data();
        final String pathAudio = data.crearAudio(login, fraseEsp);

        final Intent intent = new Intent(this, ContentMyPhrasesActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);

        if (fraseEsp.isEmpty() || fraseArb.isEmpty()) {
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Integer respuesta = null;
                    try {
                        final Usuario usuario = data.getUser(login);
                        final int usersId = data.cogerId(usuario);
                        respuesta = data.postPhrase(fraseEsp, fraseArb, type, usersId, pathAudio);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        else {
            Button grabar = (Button) findViewById(R.id.botonGrabarAudio);
            grabar.setVisibility(View.GONE);

            Button registrar = (Button) findViewById(R.id.botonRegistrarFrase);
            registrar.setVisibility(View.VISIBLE);
        }
    }
}


