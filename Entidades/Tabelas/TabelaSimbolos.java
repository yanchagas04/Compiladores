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

    public void addSimbolo(Simbolo simbolo){
        simbolos.add(simbolo);
    }
}