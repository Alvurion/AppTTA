package tta.ehu.eus.apptta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    }

    public void play(View view){
        Toast.makeText(this,R.string.sinImplementar,Toast.LENGTH_SHORT).show();
    }
}
