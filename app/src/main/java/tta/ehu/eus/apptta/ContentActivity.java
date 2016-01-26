package tta.ehu.eus.apptta;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Presentacion.Data;
import tta.ehu.eus.apptta.Vista.AudioPlayer;
import tta.ehu.eus.apptta.Vista.GeneradorCardView;


public class ContentActivity extends AppCompatActivity {

    public final static String EXTRA_LOGIN="tta.ehu.eus.apptta.EXTRA_LOGIN";
    public final static String EXTRA_TYPE="tta.ehu.eus.apptta.EXTRA_TYPE";

    public static String login;
    public static String nombreType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        String opcion = intent.getStringExtra(MenuActivity.EXTRA_ID);
        login= intent.getStringExtra(MenuActivity.EXTRA_LOGIN);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.LinearContent);

        final int type = Integer.parseInt(opcion.replaceAll("[\\D]", ""));
        nombreType = getType(type);

        final Data data = new Data();

            new Thread() {
                @Override
                public void run() {
                    try {

                        final Frase[] finalFraseArray = data.getPhrases(type);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < finalFraseArray.length; i++) {
                                    GeneradorCardView gen=new GeneradorCardView();
                                    CardView card = gen.crearCardView(finalFraseArray[i], ContentActivity.this,layout);
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
        intent.putExtra(EXTRA_LOGIN,login);
        startActivity(intent);
    }

    public void abrirMapa(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(EXTRA_TYPE,nombreType);
        startActivity(intent);
    }

    public String getType(int type){
        String t=null;
        switch(type){
            case 1:
                t="Policia";
                break;
            case 2:
                t="Ayuntamiento";
                break;
            case 3:
                t="Supermercado";
                break;
            case 4:
                t="Panadería";
                break;
            case 5:
                t="Estación";
                break;
            case 6:
                t="Farmacia";
                break;
            case 7:
                t="Hospital";
                break;
        }
        return t;
    }

}
