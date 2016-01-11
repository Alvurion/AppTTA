package tta.ehu.eus.apptta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MenuActivity extends AppCompatActivity {

     public final static String EXTRA_ID="tta.ehu.eus.apptta.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void content(View view) {
        String idCardView= view.getResources().getResourceName(view.getId());
        Intent intent= new Intent(this,ContentActivity.class);
        intent.putExtra(EXTRA_ID,idCardView);
        startActivity(intent);
    }

}
