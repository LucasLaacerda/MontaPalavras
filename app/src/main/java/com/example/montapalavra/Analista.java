package com.example.montapalavra;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class Analista {

    private String bancoPalavras[] ={"Abacaxi", "Manada", "mandar", "porta", "mesa", "Dado", "Mangas", "Já", "coisas", "radiografia",
            "matemática", "Drogas", "prédios", "implementação", "computador", "balão", "Xícara", "Tédio",
            "faixa", "Livro", "deixar", "superior", "Profissão", "Reunião", "Prédios", "Montanha", "Botânica",
            "Banheiro", "Caixas", "Xingamento", "Infestação", "Cupim", "Premiada", "empanada", "Ratos",
            "Ruído", "Antecedente", "Empresa", "Emissário", "Folga", "Fratura", "Goiaba", "Gratuito",
            "Hídrico", "Homem", "Jantar", "Jogos", "Montagem", "Manual", "Nuvem", "Neve", "Operação",
            "Ontem", "Pato", "Pé", "viagem", "Queijo", "Quarto", "Quintal", "Solto", "rota", "Selva",
            "Tatuagem", "Tigre", "Uva", "Último", "Vitupério", "Voltagem", "Zangado", "Zombaria", "Dor"};
    private String bancoPoints[][]= {{"EAIONRTLSU","1"},
                                    {"WDG","2"},
                                    {"BCMP","3"},
                                    {"FHV","4"},
                                    {"JX","8"},
                                    {"QZ","10"}};
    private String palavraInicial;
    private String matrizMatch[][];
    private String palavrasFormadas[][];
    private String palavraFinal="";
    private String letrassobraram="";
    private String pontos="";

    private int acumulador;
    private String posicoesAnalisada[][];

    public Analista(String palavraInicial) {
        this.palavraInicial = palavraInicial;
        this.matrizMatch = new String [this.bancoPalavras.length][3];
        this.palavrasFormadas = new String[this.bancoPalavras.length][3];
        this.acumulador = 0;
    }


    public void montaMatch(){
        //MatrizMatch[palavra][quantiaMatch][letrasSemMatch]
        //Analisa letra a letra se temos um match ou nao

        for(int i=0; i<=bancoPalavras.length-1;i++) {
            String palavraBanco = bancoPalavras[i];
            matrizMatch[i][0] = palavraBanco;
            matrizMatch[i][1] = "0";
            matrizMatch[i][2] = "";
            acumulador=0;
            posicoesAnalisada = new String[palavraBanco.length()][2];

            for(int z=0; z<palavraInicial.length();z++) {
                char letra = palavraInicial.charAt(z);

                int controlRepeticao = 0;
                for(int s=0;s<palavraBanco.length() && controlRepeticao!=1;s++) {
                    if(Character.toString(palavraBanco.charAt(s)).equalsIgnoreCase(Character.toString(letra))
                            && (posicoesAnalisada[s][1]==null || posicoesAnalisada[s][1].equalsIgnoreCase("false"))) {

                        //Adiciona +1 match caso a letra for encontrada na palavra
                        acumulador++;
                        matrizMatch[i][1] = Integer.toString(acumulador);
                        controlRepeticao = 1;
                        posicoesAnalisada[s][0]=Character.toString(palavraBanco.charAt(s));
                        posicoesAnalisada[s][1]="true";

                    }else if(posicoesAnalisada[s][1]==null || !posicoesAnalisada[s][1].equalsIgnoreCase("true")) {
                        //Controla posicoes ja analisadas
                        posicoesAnalisada[s][1] = "false";
                        controlRepeticao=-1;
                    }else {
                        //Controla posicoes ja analisadas
                        controlRepeticao=-1;
                    }
                }

                if(controlRepeticao==-1) {
                    //Preenche letra que nao teve match
                    matrizMatch[i][2] = ""+matrizMatch[i][2]+""+Character.toString(letra);
                }

            }


        }

    }


    public void preenchePalavrasFormadas(){
        //Monta matriz com as palavras que foram possiveis montar com as letras inseridas
        //palavrasFormadas[palavraMontada][pontuacao][letrasSemMatch]

        int quantPalavrasFormadas=0;
        for(int i=0;i<matrizMatch.length;i++) {
            if(matrizMatch[i][0].length()==Integer.parseInt(matrizMatch[i][1])) {
                palavrasFormadas[quantPalavrasFormadas][0] = matrizMatch[i][0];
                palavrasFormadas[quantPalavrasFormadas][1] = Integer.toString(0);
                palavrasFormadas[quantPalavrasFormadas][2] = matrizMatch[i][2];
                quantPalavrasFormadas++;
            }
        }
    }


    public void preenchePontuacao(){
        //Calcula e preenche pontuacao de cada letra na tabela de palavrasFormadas
        //Para entao decidir qual a melhor palavra

        for(int i=0;i<palavrasFormadas.length;i++) {
            for(int s=0;s<bancoPoints.length;s++) {
                for(int z=0;z<bancoPoints[s][0].length();z++) {
                    if(palavrasFormadas[i][0]!=null &&
                            (palavrasFormadas[i][0].contains(Character.toString(bancoPoints[s][0].charAt(z)).toLowerCase())
                                    || palavrasFormadas[i][0].contains(Character.toString(bancoPoints[s][0].charAt(z)).toUpperCase()))) {



                        //Contar quantas tem pra multiplicar pela pontuação
                        int multiplicador = 0;
                        for(int n=0;n<palavrasFormadas[i][0].length();n++) {
                            if(Character.toString(palavrasFormadas[i][0].charAt(n)).equalsIgnoreCase(Character.toString(bancoPoints[s][0].charAt(z)))) {
                                multiplicador++;
                            }
                        }


                        palavrasFormadas[i][1] = ""+(Integer.parseInt(palavrasFormadas[i][1])+(Integer.parseInt(bancoPoints[s][1])*multiplicador));

                    }
                }
            }
        }

    }

    public void exibePalavraFinal(){
        //Analisa qual a palavras que mais pontuou entre as palavras ja formadas

        int aux=0;
        String sobraram="";

        for(int i=0;i<palavrasFormadas.length;i++) {
            if(palavrasFormadas[i][0]!=null && Integer.parseInt(palavrasFormadas[i][1])>aux) {
                aux = Integer.parseInt(palavrasFormadas[i][1]);
                palavraFinal = palavrasFormadas[i][0];
                sobraram = palavrasFormadas[i][2];
            }
        }

        if(palavraFinal!=null && palavraFinal.length()>0 && !palavraFinal.isEmpty()) {

            System.out.println("\n"+palavraFinal+", palavra de "+aux+" pontos.");
            setPalavraFinal(palavraFinal);
            setPontos(Integer.toString(aux));
            if(sobraram!=null && sobraram.length()>0) {
                System.out.println("Sobraram: "+sobraram);
                setLetrasSobraram(sobraram);
            }else {
                System.out.println("Todas as letras foram utilizadas.");
                setLetrasSobraram("Todas as letras foram utilizadas.");
            }
        }else {
            System.out.println("Nenhuma palavra formada!");
            System.out.println("Sobraram: "+palavraInicial);
            setPalavraFinal(null);
            setPontos(null);
            setLetrasSobraram(null);
        }

    }


    public String getPalavraFinal() {
        return this.palavraFinal;
    }


    public void setPalavraFinal(String palavraFinal) {
        this.palavraFinal = palavraFinal;
    }

    public String getLetrasSobraram() {
        return this.letrassobraram;
    }


    public void setLetrasSobraram(String letrassobraram) {
        this.letrassobraram = letrassobraram;
    }

    public String getPontos() {
        return this.pontos;
    }


    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

}
