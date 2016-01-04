package tta.ehu.eus.apptta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        //Recogemos las variables que queremos comprobar
        EditText u = (EditText) findViewById(R.id.user);
        String user= u.getText().toString();
        EditText c1 = (EditText) findViewById(R.id.password);
        String pass1= c1.getText().toString();
        EditText c2 = (EditText) findViewById(R.id.password2);
        String pass2= c2.getText().toString();

        if(user.isEmpty() || pass1.isEmpty() || pass2.isEmpty() ){
            Toast.makeText(this, R.string.CampoVacio, Toast.LENGTH_SHORT).show();
        }
        else{
            if(pass1.equals(pass2)){
                Toast.makeText(this,R.string.sinImplementar,Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(this,R.string.noCoincide,Toast.LENGTH_SHORT).show();
            }
        }




    }
}
