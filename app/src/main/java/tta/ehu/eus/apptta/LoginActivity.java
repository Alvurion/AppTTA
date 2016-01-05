package tta.ehu.eus.apptta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        EditText u=(EditText) findViewById(R.id.TxtUsuario);
        String user=u.getText().toString();
        EditText p=(EditText) findViewById(R.id.TxtPassword);
        String pass=p.getText().toString();
        //Los tenemos cogidos para cuando tengamos la DB preparada
        if(user.isEmpty() ||pass.isEmpty() ){
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        }
        else{
            //Aqui hacer hilo mientras comprueba que es user correcto o no
            //dar acceso al menu
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            //Toast.makeText(this, R.string.sinImplementar, Toast.LENGTH_SHORT).show();
        }

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    

}
