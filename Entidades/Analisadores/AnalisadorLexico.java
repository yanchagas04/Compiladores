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

    public AnalisadorLexico(TabelaReservados tabelaReservados, TabelaSimbolos tabelaSimbolos, Escopo escopo, Scanner leitor) throws FileNotFoundException  {
        setEscopo(escopo);
        setTabelaReservados(tabelaReservados);
        setTabelaSimbolos(tabelaSimbolos);
        setLeitor(leitor);
        setLinha(1);
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
        Pattern invalidos = Pattern.compile("@|º|ª|~");
        while (leitor.hasNext(invalidos)){
            leitor.next();
        }
    }

    public boolean simbolosCombinados(){
        if (leitor.hasNext(":=") || leitor.hasNext("!=") || leitor.hasNext("<=") || leitor.hasNext("==") || leitor.hasNext(">="))
            return true;
        return false;
    }

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
                Pattern resto_variavel = Pattern.compile("[a-z]|[0-9]|_|D|P|F|V|S|E|");
                Pattern inicio_variavel = Pattern.compile("[a-z]|_");
                pularEspacos();
                pularInvalidos();
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
                } else { //COISAS QUE NÃO SÃO VARIAVEIS
                    if (simbolosCombinados()){ //SIMBOLOS COM 2 CARACTERES
                        atomo = leitor.next();
                        codigo = tabelaReservados.getTabela().get(atomo);
                    }
                    else if (leitor.hasNext("[0-9]")){ //INTEIROS
                        while (leitor.hasNext("[0-9]")) {
                            atomo += leitor.next();
                            System.out.println(atomo);
                        }
                        codigo = "C03";
                        if (leitor.hasNext("\\.")){ //REAIS
                            atomo += leitor.next();
                            codigo = "C04";
                            while (leitor.hasNext("[0-9]")) {
                                atomo += leitor.next();
                            }
                            if (leitor.hasNext("e")){
                                atomo += leitor.next();
                                if (leitor.hasNext("+") || leitor.hasNext("-")){
                                    atomo += leitor.next();
                                    while (leitor.hasNext("[0-9]")) {
                                        atomo += leitor.next();
                                    }
                                } 
                            }
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, atomo, atomo.length(), atomo.length(), "VOI", linha));   
                    }
                    else if (leitor.hasNext()){ //SIMBOLOS COM 1 CARACTERE
                        String proximo_char = leitor.next();
                        if (proximo_char.equals("\n"))
                            aumentaLinha(linha);
                        else {
                            pularEspacos();
                            pularInvalidos();
                            atomo = proximo_char;
                            if (tabelaReservados.possui(atomo)){
                                codigo = tabelaReservados.getTabela().get(atomo);
                            }
                        }
                    } /*else if () {

                    } */ else {
                        return null;
                    }
                }
                break;
            case Escopo.antesNomeFunc:
                resto_variavel = Pattern.compile("[a-z]|[0-9]");
                inicio_variavel = Pattern.compile("[a-z]");
                pularEspacos();
                pularInvalidos();
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
                } else { //COISAS QUE NÃO SÃO VARIAVEIS
                    if (simbolosCombinados()){ //SIMBOLOS COM 2 CARACTERES
                        atomo = leitor.next();
                        codigo = tabelaReservados.getTabela().get(atomo);
                    }
                    else if (leitor.hasNext("[0-9]")){ //INTEIROS
                        while (leitor.hasNext("[0-9]")) {
                            atomo += leitor.next();
                            System.out.println(atomo);
                        }
                        codigo = "C03";
                        if (leitor.hasNext("\\.")){ //REAIS
                            atomo += leitor.next();
                            codigo = "C04";
                            while (leitor.hasNext("[0-9]")) {
                                atomo += leitor.next();
                            }
                            if (leitor.hasNext("e")){
                                atomo += leitor.next();
                                if (leitor.hasNext("+") || leitor.hasNext("-")){
                                    atomo += leitor.next();
                                    while (leitor.hasNext("[0-9]")) {
                                        atomo += leitor.next();
                                    }
                                } 
                            }
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, atomo, atomo.length(), atomo.length(), "VOI", linha));   
                    }
                    else if (leitor.hasNext()){ //SIMBOLOS COM 1 CARACTERE
                        String proximo_char = leitor.next();
                        if (proximo_char.equals("\n"))
                            aumentaLinha(linha);
                        else {
                            pularEspacos();
                            pularInvalidos();
                            atomo = proximo_char;
                            if (tabelaReservados.possui(atomo)){
                                codigo = tabelaReservados.getTabela().get(atomo);
                            }
                        }
                    } /*else if () {

                    } */ else {
                        return null;
                    }
                }
                break;
            case Escopo.nomFuncao:
                Pattern inicio_funcao = Pattern.compile("[a-z]");
                Pattern resto_funcao = Pattern.compile("[a-z]|[0-9]");
                pularEspacos();
                pularInvalidos();
                if (leitor.hasNext(inicio_funcao)){
                    atomo += leitor.next();
                    System.out.println(atomo);
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_funcao)){
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
                        codigo = "C05";
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
            case Escopo.nomPrograma:
                Pattern inicio_nomPrograma = Pattern.compile("[a-z]");
                Pattern resto_nomPrograma = Pattern.compile("[a-z]|[0-9]");
                pularEspacos();
                pularInvalidos();
                if (leitor.hasNext(inicio_nomPrograma)){
                    atomo += leitor.next();
                    System.out.println(atomo);
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_nomPrograma)){
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
                        codigo = "C06";
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
                System.out.println("Escopo inválido");
                break;
        }
        return new Atomo(atomo, codigo, linha);
    }

}
