package Entidades.Analisadores;

import java.util.ArrayList;
import java.util.Enumeration;

import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;

public class AnalisadorLexico {
    private TabelaReservados tabelaReservados;
    private TabelaSimbolos tabelaSimbolos;
    private Escopo escopo;

    public AnalisadorLexico(TabelaReservados tabelaReservados, TabelaSimbolos tabelaSimbolos, Escopo escopo){
        setEscopo(escopo);
        setTabelaReservados(tabelaReservados);
        setTabelaSimbolos(tabelaSimbolos);
    }

    public TabelaReservados getTabelaReservados() {
        return tabelaReservados;
    }

    public TabelaSimbolos getTabelaSimbolos() {
        return tabelaSimbolos;
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public void setTabelaReservados(TabelaReservados tabelaReservados) {
        this.tabelaReservados = tabelaReservados;
    }

    public void setTabelaSimbolos(TabelaSimbolos tabelaSimbolos) {
        this.tabelaSimbolos = tabelaSimbolos;
    }

    public void setEscopo(Escopo escopo) {
        this.escopo = escopo;
    }

    public ArrayList<Character> addCharsNoEscopo(String sequencia){
        ArrayList <Character> charsEscopo = new ArrayList<>();
        for (int i = 0; i < sequencia.length(); i++){
            charsEscopo.add(sequencia.toCharArray()[i]);
        }
        return charsEscopo;
    }   

    public Atomo formarAtomo(){
        ArrayList <Character> charsEscopo = new ArrayList<>();
        switch (this.escopo) {
            case variavel:
                charsEscopo = addCharsNoEscopo("abcdefghijklmnopqrstuvwxyz_1234567890");
                String atomo = "";
                char charLido = ' ';
                while (charsEscopo.contains(charLido)){
                    atomo = atomo + charLido;
                }
                
                break;
        
            default:
                break;
        }
        return null;
    }
}
