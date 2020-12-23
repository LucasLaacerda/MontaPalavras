package com.example.montapalavra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        Intent intent = getIntent();

        String palavraMontada = intent.getStringExtra(MainActivity.palavraMontada);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("'"+palavraMontada+"'");

        String pontos = intent.getStringExtra(MainActivity.palavraPontos);
        TextView pontosView = findViewById(R.id.pontosView);
        pontosView.setText(pontos);

        if(MainActivity.sobraram!=null) {
            String sobraram = intent.getStringExtra(MainActivity.sobraram);
            TextView sobraramView = findViewById(R.id.sobraramView);
            sobraramView.setText(sobraram);
        }

    }
}