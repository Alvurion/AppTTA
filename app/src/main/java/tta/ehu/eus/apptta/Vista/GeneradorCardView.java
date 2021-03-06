package tta.ehu.eus.apptta.Vista;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.R;


public class GeneradorCardView {

    public GeneradorCardView() {
    }

    public CardView crearCardView(Frase frase, Context context, final View layout) {
        CardView card = new CardView(new ContextThemeWrapper(context, R.style.CardViewStyle), null, 0);
        RelativeLayout cardInner = new RelativeLayout(new ContextThemeWrapper(context, R.style.Widget_CardContent));

        RelativeLayout.LayoutParams paramsMW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin = 5;
        paramsMW.setMargins(margin, margin, margin, margin);
        cardInner.setLayoutParams(paramsMW);

        RelativeLayout.LayoutParams paramsWWPrueba = new RelativeLayout.LayoutParams(200, 200);

        //Generar el ImageButton para el play
        ImageButton ib = new ImageButton(context);
        ib.setId('1');// El ID da igual que no fueran unicos solo importa que sea positivo según la documentación del tipo View
        int ID_IB = ib.getId();
        ib.setTag(frase.getAudioFrase());
        ib.setLayoutParams(paramsWWPrueba);
        ib.setImageResource(R.drawable.play);

        //Comprobación si es en local o remoto
        String audioFrase = frase.getAudioFrase();
        final boolean comprobacion;
        if (audioFrase.contains("mp3")) {
            comprobacion = true;
        } else {
            comprobacion = false;
        }
        //Generamos la función que realiza el boton
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v, layout, comprobacion);
            }
        });
        ib.setBackground(null);
        cardInner.addView(ib);

        //Generar el TextView de frase en Español
        TextView tvEs = new TextView(context);
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

        //Generar el TextView de frase en Arabe
        TextView tvAr = new TextView(context);
        RelativeLayout.LayoutParams paramsWW3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsWW3.addRule(RelativeLayout.RIGHT_OF, ID_IB);
        paramsWW3.addRule(RelativeLayout.BELOW, ID_TvES);
        tvAr.setLayoutParams(paramsWW3);
        tvAr.setText(frase.getPhraseAr());
        tvAr.setTextSize(15);
        cardInner.addView(tvAr);

        TextView codigoAudio = new TextView(context);
        codigoAudio.setText(frase.getAudioFrase());
        codigoAudio.setVisibility(View.GONE);
        cardInner.addView(codigoAudio);

        //Se unen todas las vistas en una de cardview
        card.addView(cardInner);
        card.setCardElevation(5);
        card.setUseCompatPadding(true);
        return card;
    }


    public void play(View view, View layout, Boolean comprobacion) {

        Data data = new Data();
        //Se recoge la uri correspondiente segun si es en local o remoto
        Uri uri = data.getURL(comprobacion, view);

        LinearLayout linear = (LinearLayout) layout.findViewById(R.id.LinearContent);
        AudioPlayer audioPlayer = new AudioPlayer(linear);
        try {
            audioPlayer.setAudioUri(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
