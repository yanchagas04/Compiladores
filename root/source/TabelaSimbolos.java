package source;

import java.util.ArrayList;

public class TabelaSimbolos {

    private ArrayList<Simbolo> simbolos;
    private String nome_arquivo;

    public TabelaSimbolos(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
        simbolos = new ArrayList<Simbolo>();
        
    }

    @Override
    public String toString(){
        String retorno = "";
        retorno = "Código da Equipe: 04\n";
        retorno = retorno + "Componentes:\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n\n";
        retorno = retorno + "RELATÓRIO DA ANÁLISE DA TABELA DE SÍMBOLOS. Texto fonte analisado: " + nome_arquivo + "\n";
        retorno = retorno + "\n\n";
        for (Simbolo simb : simbolos){
            retorno = retorno + simb.toString() + "\n\n";
        }
        return retorno;
    }

    public int proximaEntrada(){
        return simbolos.size() + 1;
    }

    public void addSimbolo(Simbolo simbolo){
        simbolos.add(simbolo);
    }

    public void addLinhaAoSimbolo(String simbolo, int linha){
        for (Simbolo simb : simbolos){
            if (simb.getLexeme().equals(simbolo)){
                if (simb.getLinhas().size() == 5)
                    return;
                simb.addLinhas(linha);
                return;
            }
        }
    }

    public boolean possui(String simbolo, String codigo){

        for (Simbolo simb : simbolos){
            if (simb.getLexeme().equals(simbolo))
                return true;
        }
        return false;
    }
}