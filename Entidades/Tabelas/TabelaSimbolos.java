package Entidades.Tabelas;
import java.util.ArrayList;

public class TabelaSimbolos {

    private ArrayList<Simbolo> simbolos;

    public TabelaSimbolos() {
        simbolos = new ArrayList<Simbolo>();
        
    }

    @Override
    public String toString(){
        String retorno = "";
        for (Simbolo simb : simbolos){
            retorno = retorno + simb.toString() + "\n";
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