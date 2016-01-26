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
import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentacion.Data;
import tta.ehu.eus.apptta.Vista.AudioPlayer;

public class ContentMyPhrasesActivity extends AppCompatActivity {


    public final static String EXTRA_LOGIN="tta.ehu.eus.apptta.EXTRA_LOGIN";
    public static String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_my_phrases);
        Intent intent = getIntent();
        login= intent.getStringExtra(MenuActivity.EXTRA_LOGIN);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.LinearContent);
        final Data data = new Data();

        new Thread() {
            @Override
            public void run() {
                try {
                    final Usuario usuario= data.getUser(login);
                    final int usersId =data.cogerId(usuario);
                    final Frase[] finalFraseArray = data.getPhrasesUser(usersId);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < finalFraseArray.length; i++) {
                                CardView card = crearCardView(finalFraseArray[i]);
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
    public CardView crearCardView(Frase frase){
        CardView card= new CardView(new ContextThemeWrapper(ContentMyPhrasesActivity.this,R.style.CardViewStyle),null,0);
        RelativeLayout cardInner = new RelativeLayout(new ContextThemeWrapper(ContentMyPhrasesActivity.this,R.style.Widget_CardContent));

        RelativeLayout.LayoutParams paramsMW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin = 5;
        paramsMW.setMargins(margin, margin, margin, margin);
        cardInner.setLayoutParams(paramsMW);


        RelativeLayout.LayoutParams paramsWW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsWWPrueba= new RelativeLayout.LayoutParams(200,200);
        ImageButton ib = new ImageButton(this);
        // El ID da igual que no fueran unicos solo importa que sea positivo según la documentación del tipo View
        ib.setId('1');
        int ID_IB = ib.getId();
        ib.setTag(frase.getAudioFrase());
        ib.setLayoutParams(paramsWWPrueba);
        ib.setImageResource(R.drawable.play);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });
        ib.setBackground(null);
        cardInner.addView(ib);


        TextView tvEs = new TextView(this);
        tvEs.setId('4');
        int ID_TvES = tvEs.getId();
        RelativeLayout.LayoutParams paramsWW2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsWW2.addRule(RelativeLayout.RIGHT_OF, ID_IB);
        paramsWW2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tvEs.setLayoutParams(paramsWW2);
        tvEs.setText(frase.getPhraseEs());
        tvEs.setTextSize(20);
        tvEs.setTextColor(Color.BLACK);
        cardInner.addView(tvEs);

        TextView tvAr = new TextView(this);
        RelativeLayout.LayoutParams paramsWW3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsWW3.addRule(cardInner.RIGHT_OF, ID_IB);
        paramsWW3.addRule(cardInner.BELOW, ID_TvES);
        tvAr.setLayoutParams(paramsWW3);
        tvAr.setText(frase.getPhraseAr());
        tvAr.setTextSize(15);
        cardInner.addView(tvAr);

        TextView codigoAudio= new TextView(this);
        codigoAudio.setText(frase.getAudioFrase());
        codigoAudio.setVisibility(View.GONE);
        cardInner.addView(codigoAudio);

        card.addView(cardInner);
        card.setCardElevation(5);
        card.setUseCompatPadding(true);
        return card;
    }

    public void play(View view) {
        String basePlay="http://10.106.25.244:8080/RefugiApp/file/";
        String url= (String) view.getTag();
        String urlcompleta= basePlay+url;
        Uri uri = Uri.parse(urlcompleta);
        LinearLayout linear = (LinearLayout) findViewById(R.id.LinearContent);
        AudioPlayer audioPlayer = new AudioPlayer(linear);
        try {
            audioPlayer.setAudioUri(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void registerPhrase(View view) {
        Intent intent = new Intent(this, PhraseRegisterActivity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        startActivity(intent);
    }
}
