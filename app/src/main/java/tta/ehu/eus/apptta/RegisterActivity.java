package tta.ehu.eus.apptta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tta.ehu.eus.apptta.Comunicaciones.RestClient;
import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentacion.Data;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(final View view){
        //Recogemos las variables que queremos comprobar
        EditText u = (EditText) findViewById(R.id.user);
        final String user= u.getText().toString();
        EditText c1 = (EditText) findViewById(R.id.password);
        final String pass1= c1.getText().toString();
        EditText c2 = (EditText) findViewById(R.id.password2);
        String pass2= c2.getText().toString();
        final Intent intent = new Intent(this, LoginActivity.class);


        if(user.isEmpty() || pass1.isEmpty() || pass2.isEmpty() ){
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        }
        else{
            if(pass1.equals(pass2)){
                final Data data = new Data();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Usuario usuario = null;
                        Integer respuesta = null;
                        try {
                            respuesta = data.postUser(user, pass1);
                        } catch (Exception e) {
                            Log.e("ALERTA", e.getMessage(), e);
                        } finally {
                            final Integer finalRespuesta = respuesta;
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalRespuesta ==200){
                                        Toast.makeText(getApplicationContext(),R.string.exito,Toast.LENGTH_SHORT).show();
                                        startActivity(intent);}
                                    else{
                                        Toast.makeText(getApplicationContext(),R.string.registroFallido,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                }).start();
            }
            else{
                Toast.makeText(this,R.string.noCoincide,Toast.LENGTH_SHORT).show();
            }
        }




    }
}
