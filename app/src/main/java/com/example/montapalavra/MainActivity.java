package com.example.montapalavra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /* A lógica do programa se baseia em percorrer cada letra das palavras do banco comparando com as letras inseridas,
     * a cada letra encontrada na palavra, programa insere +1 match na matriz de matches.
     *
     * Assim no final do metodo "montaMatch();", é montado uma matriz com 3 colunas:
     * [1] Respectiva palavra do banco
     * [2] Quantidade de matches que a palavra teve com as letras inseridas na jogada
     * [3] Letras que não obtiveram match com a palavra do banco
     *
     * Em seguida o metodo "preenchePalavrasFormadas();"
     * monta outra matriz mas com apenas as palavras que obitiveram uma quantia de matches iguais ao seu tamanho,
     * ou seja, apenas com as palavras que foram possiveis montar por completo com as letras inseridas.
     *
     * Logo apos descobrir quais letras foram possiveis montar, o metodo "preenchePontuacao();"
     * analisa as letras de cada palavra junto ao banco de pontuacao, no final temos um matriz:
     * com todas as palavras formadas,suas respectivas pontuacoes e as letras que nao foram utilizadas.
     *
     * Por fim, o metodo "exibePalavraFinal();" analisa qual foi a palavra que mais pontuou entre todas formadas.
     *
     */




    private EditText editPalavraInicial;

    public static final String palavraMontada = "com.example.montaPalavra.MONTADA";
    public static final String palavraPontos = "com.example.montaPalavra.PONTOS";
    public static final String sobraram = "com.example.montaPalavra.SOBRARAM";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPalavraInicial = findViewById(R.id.editPalavraInicial);

    }


    public void montaPalavra(View view){

        String jogadaInicial = editPalavraInicial.getText().toString();
        Analista analista = new Analista(jogadaInicial);
        analista.montaMatch();
        analista.preenchePalavrasFormadas();
        analista.preenchePontuacao();
        analista.exibePalavraFinal();

        if(analista.getPalavraFinal() != null && analista.getPalavraFinal().length()>0) {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(palavraMontada, analista.getPalavraFinal() );
            intent.putExtra(palavraPontos, analista.getPontos());
            intent.putExtra(sobraram, analista.getLetrasSobraram());
            startActivity(intent);
        }else{
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            alertBox.setTitle("Analise sem resultado");
            alertBox.setIcon(android.R.drawable.ic_dialog_info);
            alertBox.setMessage("Nenhuma palavra foi formada.");
            alertBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this,"Informe novas palavras",Toast.LENGTH_SHORT).show();
                }
            });

            alertBox.show();
        }


    }

}