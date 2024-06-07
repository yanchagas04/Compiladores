package Entidades.Analisadores;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import Entidades.Tabelas.Simbolo;
import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;

public class AnalisadorLexico {
    private TabelaReservados tabelaReservados;
    private TabelaSimbolos tabelaSimbolos;
    private Escopo escopo;
    private Scanner leitor;
    private int linha;

    public AnalisadorLexico(TabelaReservados tabelaReservados, TabelaSimbolos tabelaSimbolos, Escopo escopo, Scanner leitor, int linha) throws FileNotFoundException  {
        setEscopo(escopo);
        setTabelaReservados(tabelaReservados);
        setTabelaSimbolos(tabelaSimbolos);
        setLeitor(leitor);
        setLinha(linha);
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

    public void setLeitor(Scanner leitor) {
        this.leitor = leitor;
    }
    
    public Scanner getLeitor() {
        return leitor;
    }
    // public ArrayList<Character> addCharsNoEscopo(String sequencia){
    //     ArrayList <Character> charsEscopo = new ArrayList<>();
    //     for (int i = 0; i < sequencia.length(); i++){
    //         charsEscopo.add(sequencia.toCharArray()[i]);
    //     }
    //     return charsEscopo;
    // }   

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void aumentaLinha(int linha) {
        setLinha(linha + 1);
  
    }

    public void pularEspacos(){
        while (leitor.hasNext(" ")){
            leitor.next();
        }
    }
    
    public void pularInvalidos(){
        Pattern invalidos = Pattern.compile("@|,|º|ª|~");
        while (leitor.hasNext(invalidos)){
            leitor.next();
        }
    }

    // public boolean fimDeArquivo(){
    //     return !leitor.hasNext();
    // }

    public Atomo formarAtomo(){
        //ArrayList <Character> charsEscopo = new ArrayList<>();
        String atomo = "";
        String codigo = null;
        // if (fimDeArquivo())
        //     return null;
        //aumentaLinha(linha);
        pularInvalidos();
        switch (this.escopo) {
            case Escopo.variavel:
                Pattern resto_variavel = Pattern.compile("[a-z]|[0-9]|_");
                Pattern inicio_variavel = Pattern.compile("[a-z]|_");
                pularEspacos();
                if (leitor.hasNext(inicio_variavel)){
                    atomo += leitor.next();
                    System.out.println(atomo);
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_variavel)){
                            atomo += leitor.next();
                            System.out.println(atomo);
                        } else {
                            break;
                        }
                    }
                    if (tabelaReservados.possui(atomo)){
                        codigo = tabelaReservados.getTabela().get(atomo);
                    } else if (tabelaSimbolos.possui(atomo)) {
                        tabelaSimbolos.addLinhaAoSimbolo(atomo, linha);
                    } else {
                        codigo = "C07";
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, atomo, atomo.length(), atomo.length(), "VOI", linha));
                    }
                } else {
                    if (leitor.hasNext()){
                        String proximo_char = leitor.next();
                        if (proximo_char.equals("\n"))
                            aumentaLinha(linha);
                    } else {
                        return null;
                    }
                }
                break;
        
            default:
                break;
        }
        return new Atomo(atomo, codigo, linha);
    }

}
