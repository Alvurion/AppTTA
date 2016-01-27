package tta.ehu.eus.apptta.Presentador.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tta.ehu.eus.apptta.Modelo.Frase;
import tta.ehu.eus.apptta.Modelo.Usuario;
import tta.ehu.eus.apptta.Presentador.Data;
import tta.ehu.eus.apptta.R;
import tta.ehu.eus.apptta.Vista.GeneradorCardView;

public class ContentMyPhrasesActivity extends AppCompatActivity {


    public final static String EXTRA_LOGIN = "tta.ehu.eus.apptta.EXTRA_LOGIN";
    public String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_my_phrases);
        Intent intent = getIntent();
        login = intent.getStringExtra(MenuActivity.EXTRA_LOGIN);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.LinearContent);
        final Data data = new Data();

        new Thread() {
            @Override
            public void run() {
                try {
                    final Usuario usuario = data.getUser(login);
                    final int usersId = data.cogerId(usuario);
                    final Frase[] finalFraseArray = data.getContentUser(usersId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalFraseArray.length != 0) {
                                for (int i = 0; i < finalFraseArray.length; i++) {
                                    GeneradorCardView gen = new GeneradorCardView();
                                    CardView card = gen.crearCardView(finalFraseArray[i], ContentMyPhrasesActivity.this, layout);
                                    layout.addView(card);
                                }
                            } else {

                                TextView nofrase = (TextView) findViewById(R.id.Nofrase);
                                nofrase.setVisibility(View.VISIBLE);
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
        finish();
    }
}
