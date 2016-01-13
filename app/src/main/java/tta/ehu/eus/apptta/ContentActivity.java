package tta.ehu.eus.apptta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;

import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentacion.Data;


public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent= getIntent();
        String opcion= intent.getStringExtra(MenuActivity.EXTRA_ID);
        final View view= findViewById(R.id.LinearContent);
        final LinearLayout layout=(LinearLayout)findViewById(R.id.LinearContent);

        final int type=Integer.parseInt(opcion.replaceAll("[\\D]", ""));
        

        //Character.getNumericValue(opcion.charAt(7));

        final Data data = new Data();
        new Thread(){
            @Override
            public void run() {

                try {
                    final Frase[] finalFraseArray = data.getPhrases(type);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < finalFraseArray.length; i++) {
                                CardView card =  crearCardView(finalFraseArray[i]);
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

    public void play(View view){
        Toast.makeText(this,R.string.sinImplementar,Toast.LENGTH_SHORT).show();
    }

    public CardView crearCardView(Frase frase){
        CardView card= new CardView(new ContextThemeWrapper(ContentActivity.this,R.style.CardViewStyle),null,0);
        RelativeLayout cardInner = new RelativeLayout(new ContextThemeWrapper(ContentActivity.this,R.style.Widget_CardContent));

        RelativeLayout.LayoutParams paramsMW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin=5;
        paramsMW.setMargins(margin,margin,margin,margin);
        cardInner.setLayoutParams(paramsMW);


        RelativeLayout.LayoutParams paramsWW = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageButton ib= new ImageButton(this);
        ib.setId('1');
        int ID_IB=ib.getId();
        ib.setLayoutParams(paramsWW);
        ib.setImageResource(R.drawable.play);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });
        cardInner.addView(ib);


        TextView tvEs= new TextView(this);
        tvEs.setId('4');
        int ID_TvES=tvEs.getId();
        RelativeLayout.LayoutParams paramsWW2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsWW2.addRule(RelativeLayout.RIGHT_OF,ID_IB);
        paramsWW2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tvEs.setLayoutParams(paramsWW2);
        tvEs.setText(frase.getPhraseEs());
        tvEs.setTextSize(20);
        cardInner.addView(tvEs);

        TextView tvAr= new TextView(this);
        RelativeLayout.LayoutParams paramsWW3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        paramsWW3.addRule(cardInner.RIGHT_OF,ID_IB);
        paramsWW3.addRule(cardInner.BELOW,ID_TvES);
        tvAr.setLayoutParams(paramsWW3);
        tvAr.setText(frase.getPhraseAr());

        cardInner.addView(tvAr);
        card.addView(cardInner);
        return card;
    }

}
