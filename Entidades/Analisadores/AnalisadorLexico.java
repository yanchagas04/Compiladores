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

    public String truncar(String atomo, String codigo){
        String retorno;
        if (atomo.length() > 30){
            if (codigo.equals("C01")){
                retorno = atomo.substring(0, 29);
                retorno += "\"";
            } if (codigo.equals("C04")){
                retorno = atomo.substring(0, 30);
                if (retorno.charAt(29) == '+' || retorno.charAt(29) == '-'){
                    retorno = retorno.substring(0, 29);
                } else if (retorno.charAt(29) == '.'){
                    retorno = retorno.substring(0, 29);
                }
            } else{
                retorno = atomo.substring(0, 30);
            }
        } else {
            retorno = atomo;
        }
        return retorno;
    }

    public String trocarCodigo(String atomo, String codigo){
        if (codigo.equals("C04") && !truncar(atomo, codigo).contains(".")) {
            return "C03";
        }
        return codigo;
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
                    }
                    else {
                        codigo = "C07";
                        if (tabelaSimbolos.possui(atomo, codigo)) {
                            tabelaSimbolos.addLinhaAoSimbolo(atomo, linha);
                        } else {
                            tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
                        }
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
                                if (leitor.hasNext("\\+") || leitor.hasNext("-") || leitor.hasNext("[0-9]")) {
                                    atomo += leitor.next();
                                    while (leitor.hasNext("[0-9]")) {
                                        atomo += leitor.next();
                                    }
                                } 
                            }
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), trocarCodigo(atomo, codigo), truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));   
                    } else if (leitor.hasNext("\'")){ //CARACTER
                        atomo += leitor.next();
                        if (leitor.hasNext("[a-z]")){
                            atomo += leitor.next();
                            if (leitor.hasNext("\'")){
                                atomo += leitor.next();
                                codigo = "C02";
                                tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, atomo, atomo.length(), atomo.length(), "VOI", linha));
                            }
                        } else{
                            atomo = "";
                            codigo = null;
                        }
                    } else if (leitor.hasNext("\"")) { //STRING
                        atomo += leitor.next();
                        while (leitor.hasNext("[a-z]| |\\$|_|[0-9]|\\."))
                            atomo += leitor.next();
                        if (leitor.hasNext("\""))
                            atomo += leitor.next();
                        codigo = "C01";
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
                    }
                    else {
                        codigo = "C07";
                        if (tabelaSimbolos.possui(atomo, codigo)) {
                            tabelaSimbolos.addLinhaAoSimbolo(atomo, linha);
                        } else {
                            tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
                        }
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
                                if (leitor.hasNext("\\+") || leitor.hasNext("-") || leitor.hasNext("[0-9]")) {
                                    atomo += leitor.next();
                                    while (leitor.hasNext("[0-9]")) {
                                        atomo += leitor.next();
                                    }
                                } 
                            }
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));   
                    }
                    else if (leitor.hasNext("\'")){ //CARACTER
                        atomo += leitor.next();
                        if (leitor.hasNext("[a-z]")){
                            atomo += leitor.next();
                            if (leitor.hasNext("\'")){
                                atomo += leitor.next();
                                codigo = "C02";
                                tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, atomo, atomo.length(), atomo.length(), "VOI", linha));
                            }
                        } else{
                            atomo = "";
                            codigo = null;
                        }
                    } else if (leitor.hasNext("\"")) { //STRING
                        atomo += leitor.next();
                        while (leitor.hasNext("[a-z]| |\\$|_|[0-9]|\\."))
                            atomo += leitor.next();
                        if (leitor.hasNext("\""))
                            atomo += leitor.next();
                        codigo = "C01";
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
                    } else if (tabelaSimbolos.possui(atomo, codigo)) {
                        tabelaSimbolos.addLinhaAoSimbolo(atomo, linha);
                    } else {
                        codigo = "C05";
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
                    } else if (tabelaSimbolos.possui(atomo, codigo)) {
                        tabelaSimbolos.addLinhaAoSimbolo(atomo, linha);
                    } else {
                        codigo = "C06";
                        tabelaSimbolos.addSimbolo(new Simbolo(tabelaSimbolos.proximaEntrada(), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
