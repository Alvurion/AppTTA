package tta.ehu.eus.apptta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends AppCompatActivity {

    public final static String EXTRA_ID="tta.ehu.eus.apptta.EXTRA_ID";
    public final static String EXTRA_LOGIN="tta.ehu.eus.apptta.EXTRA_LOGIN";
    public static String login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent= getIntent();
        login= (String) intent.getSerializableExtra(LoginActivity.EXTRA_LOGIN);

    }
    public void content(View view) {
        String idCardView= view.getResources().getResourceName(view.getId());
        Intent intent= new Intent(this,ContentActivity.class);
        intent.putExtra(EXTRA_ID, idCardView);
        intent.putExtra(EXTRA_LOGIN, login);
        startActivity(intent);
    }
    public void mycontent(View view) {
        String idCardView= view.getResources().getResourceName(view.getId());
        Intent intent= new Intent(this,ContentMyPhrasesActivity.class);
        intent.putExtra(EXTRA_ID, idCardView);
        intent.putExtra(EXTRA_LOGIN, login);
        startActivity(intent);
    }
}
