package tta.ehu.eus.apptta.Presentador.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;

import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.R;
import tta.ehu.eus.apptta.Vista.GeneradorCardView;

public class ContentActivity extends AppCompatActivity {

    public final static String EXTRA_LOGIN = "tta.ehu.eus.apptta.EXTRA_LOGIN";
    public final static String EXTRA_TYPE = "tta.ehu.eus.apptta.EXTRA_TYPE";

    public String login;
    public String nombreType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        Intent intent = getIntent();
        String opcion = intent.getStringExtra(MenuActivity.EXTRA_ID);
        login = intent.getStringExtra(MenuActivity.EXTRA_LOGIN);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.LinearContent);

        final Data data = new Data();
        final int type = Integer.parseInt(opcion.replaceAll("[\\D]", ""));
        nombreType = data.getType(type);


        new Thread() {
            @Override
            public void run() {
                try {

                    final Frase[] finalFraseArray = data.getPhrases(type);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < finalFraseArray.length; i++) {
                                GeneradorCardView gen = new GeneradorCardView();
                                CardView card = gen.crearCardView(finalFraseArray[i], ContentActivity.this, layout);
                                layout.addView(card);
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("ALERTA", e.getMessage(), e);
                }
            }
        }.start();
    }


    public void registerPhrase(View view) {
        Intent intent = new Intent(this, PhraseRegisterActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        startActivity(intent);
    }

    public void abrirMapa(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(EXTRA_TYPE, nombreType);
        startActivity(intent);
    }


}
