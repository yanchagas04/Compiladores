import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    
    public void pularComentarios(){
        if (leitor.hasNext("\\/")){
            leitor.next();
            if (leitor.hasNext("\\/")){
                leitor.next();
                while (leitor.hasNext(".")){
                   leitor.next(); 
                }
            }
            else if (leitor.hasNext("\\*")){
                leitor.next();
                while (!leitor.hasNext("\\*") || !leitor.hasNext()){
                   if (!leitor.hasNext())
                       break;
                   if (leitor.next() == "\n"){
                       aumentaLinha(linha);
                   } 
                }
                if (!leitor.hasNext())
                       return;
                if (leitor.hasNext("\\/"))
                    leitor.next();
            }
            //adicionar no LEX dps
        }
    }
    
    public void pularInvalidos(){
        Pattern invalidos = Pattern.compile("[^a-zA-Z0-9_\\$\'\"\\+\\-\\. \n\\[\\]\\(\\)\\{\\};:=!\\?%#<>/,]");
        while (leitor.hasNext(invalidos)){
            leitor.next();
        }
    }

    public boolean simbolosCombinados(){
        leitor.useDelimiter("[^(\\:=|\\!=|\\<=|==|\\>=)]");
        if (leitor.hasNext(":=") || leitor.hasNext("!=") || leitor.hasNext("<=") || leitor.hasNext("==") || leitor.hasNext(">=")){
            leitor.useDelimiter("");
            return true;
        }
        leitor.useDelimiter("");
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
        return retorno.toUpperCase();
    }

    public String trocarCodigo(String atomo, String codigo){
        if (codigo.equals("C04") && !truncar(atomo, codigo).contains(".")) {
            return "C03";
        }
        return codigo;
    }

    public Atomo formarAtomo(){
        String atomo = "";
        String codigo = null;
        pularComentarios();
        if (!leitor.hasNext()){
            return null;
        }
        pularInvalidos();
        String nova_entrada = "" + tabelaSimbolos.proximaEntrada();
        switch (this.escopo) {
            case Escopo.variavel:
                Pattern resto_variavel = Pattern.compile("[a-zA-Z]|[0-9]|_");
                Pattern inicio_variavel = Pattern.compile("[a-zA-Z]|_");
                pularInvalidos();
                if (leitor.hasNext(inicio_variavel)){
                    atomo += leitor.next();
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_variavel)){
                            atomo += leitor.next();
                        } else {
                            break;
                        }
                    }
                    if (tabelaReservados.possui(atomo.toUpperCase())){
                        codigo = tabelaReservados.getTabela().get(atomo.toUpperCase());
                        nova_entrada = "N/A";
                    }
                    else {
                        codigo = "C07";
                        if (tabelaSimbolos.possui(truncar(atomo, codigo), codigo)) {
                            tabelaSimbolos.addLinhaAoSimbolo(truncar(atomo, codigo), linha);
                        } else {
                            tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
                        }
                    }
                } else { //COISAS QUE NÃO SÃO VARIAVEIS
                    if (leitor.hasNext("[0-9]")){ //INTEIROS
                        while (leitor.hasNext("[0-9]")) {
                            atomo += leitor.next();
                        }
                        String tipo = "INT";
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
                            tipo = "PFO";
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), trocarCodigo(atomo, codigo), truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), tipo, linha));   
                    } else if (leitor.hasNext("\'")){ //CARACTER
                        atomo += leitor.next();
                        if (leitor.hasNext("[a-zA-Z]")){
                            atomo += leitor.next();
                            if (leitor.hasNext("\'")){
                                atomo += leitor.next();
                                codigo = "C02";
                                tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, atomo.toUpperCase(), atomo.length(), atomo.length(), "CHC", linha));
                            }
                        } else{
                            atomo = "";
                            codigo = null;
                        }
                    } else if (leitor.hasNext("\"")) { //STRING
                        atomo += leitor.next();
                        while (leitor.hasNext("[a-zA-Z]| |\\$|_|[0-9]|\\."))
                            atomo += leitor.next();
                        if (leitor.hasNext("\""))
                            atomo += leitor.next();
                        codigo = "C01";
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "STR", linha));
                    }
                    else if (leitor.hasNext()){ //SIMBOLOS COM 1 CARACTERE
                        String proximo_char = leitor.next();
                        if (proximo_char.equals("\n"))
                            aumentaLinha(linha);
                        else {
                            
                            pularInvalidos();
                            atomo = proximo_char;
                            if ((atomo.equals("<") || atomo.equals(">") || atomo.equals("=") || atomo.equals(":")) && leitor.hasNext("=")){
                                atomo += leitor.next(); //SIMBOLOS COM 2 CARACTERES
                            }
                            if (tabelaReservados.possui(atomo)){
                                codigo = tabelaReservados.getTabela().get(atomo);
                                nova_entrada = "N/A";
                            }
                        }
                    } else {
                        return null;
                    }
                }
                break;
            case Escopo.antesNomeFunc:
                resto_variavel = Pattern.compile("[a-zA-Z]|[0-9]");
                inicio_variavel = Pattern.compile("[a-zA-Z]");
                
                pularInvalidos();
                if (leitor.hasNext(inicio_variavel)){
                    atomo += leitor.next();
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_variavel)){
                            atomo += leitor.next();
                        } else {
                            break;
                        }
                    }
                    if (tabelaReservados.possui(atomo.toUpperCase())){
                        codigo = tabelaReservados.getTabela().get(atomo.toUpperCase());
                        nova_entrada = "N/A";
                    }
                    else {
                        codigo = "C07";
                        if (tabelaSimbolos.possui(truncar(atomo, codigo), codigo)) {
                            tabelaSimbolos.addLinhaAoSimbolo(truncar(atomo, codigo), linha);
                        } else {
                            tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
                        }
                    }
                } else { //COISAS QUE NÃO SÃO VARIAVEIS
                    if (leitor.hasNext("[0-9]")){ //INTEIROS
                        while (leitor.hasNext("[0-9]")) {
                            atomo += leitor.next();
                        }
                        String tipo = "INT";
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
                            tipo = "PFO";
                        }
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), trocarCodigo(atomo, codigo), truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), tipo, linha));   
                    }
                    else if (leitor.hasNext("\'")){ //CARACTER
                        atomo += leitor.next();
                        if (leitor.hasNext("[a-zA-Z]")){
                            atomo += leitor.next();
                            if (leitor.hasNext("\'")){
                                atomo += leitor.next();
                                codigo = "C02";
                                tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, atomo.toUpperCase(), atomo.length(), atomo.length(), "CHC", linha));
                            }
                        } else{
                            atomo = "";
                            codigo = null;
                        }
                    } else if (leitor.hasNext("\"")) { //STRING
                        atomo += leitor.next();
                        while (leitor.hasNext("[a-zA-Z]| |\\$|_|[0-9]|\\."))
                            atomo += leitor.next();
                        if (leitor.hasNext("\""))
                            atomo += leitor.next();
                        codigo = "C01";
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "STR", linha));
                    }
                    else if (leitor.hasNext()){ //SIMBOLOS COM 1 CARACTERE
                        String proximo_char = leitor.next();
                        if (proximo_char.equals("\n"))
                            aumentaLinha(linha);
                        else {
                            pularInvalidos();
                            atomo += proximo_char;
                            if ((atomo.equals("<") || atomo.equals(">") || atomo.equals("=") || atomo.equals(":")) && leitor.hasNext("=")){
                                atomo += leitor.next(); //SIMBOLOS COM 2 CARACTERES
                            }
                            if (tabelaReservados.possui(atomo)){
                                codigo = tabelaReservados.getTabela().get(atomo);
                                nova_entrada = "N/A";
                            }
                        }
                    } /*else if () {

                    } */ else {
                        return null;
                    }
                }
                break;
            case Escopo.nomFuncao:
                Pattern inicio_funcao = Pattern.compile("[a-zA-Z]");
                Pattern resto_funcao = Pattern.compile("[a-zA-Z]|[0-9]");
                
                pularInvalidos();
                if (leitor.hasNext(inicio_funcao)){
                    atomo += leitor.next();
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_funcao)){
                            atomo += leitor.next();
                        } else {
                            break;
                        }
                    }
                    if (tabelaReservados.possui(atomo.toUpperCase())){
                        codigo = tabelaReservados.getTabela().get(atomo.toUpperCase());
                        nova_entrada = "N/A";
                    } else if (tabelaSimbolos.possui(atomo.toUpperCase(), codigo)) {
                        tabelaSimbolos.addLinhaAoSimbolo(atomo.toUpperCase(), linha);
                    } else {
                        codigo = "C05";
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
                Pattern inicio_nomPrograma = Pattern.compile("[a-zA-Z]");
                Pattern resto_nomPrograma = Pattern.compile("[a-zA-Z]|[0-9]");
                
                pularInvalidos();
                if (leitor.hasNext(inicio_nomPrograma)){
                    atomo += leitor.next();
                    while (true) {
                        pularInvalidos();
                        if (leitor.hasNext(resto_nomPrograma)){
                            atomo += leitor.next();
                        } else {
                            break;
                        }
                    }
                    if (tabelaReservados.possui(atomo.toUpperCase())){
                        codigo = tabelaReservados.getTabela().get(atomo.toUpperCase());
                        nova_entrada = "N/A";
                    } else if (tabelaSimbolos.possui(atomo.toUpperCase(), codigo)) {
                        tabelaSimbolos.addLinhaAoSimbolo(atomo.toUpperCase(), linha);
                    } else {
                        codigo = "C06";
                        tabelaSimbolos.addSimbolo(new Simbolo(Integer.parseInt(nova_entrada), codigo, truncar(atomo, codigo), atomo.length(), truncar(atomo, codigo).length(), "VOI", linha));
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
        return new Atomo(atomo, codigo, linha, nova_entrada);
    }

}
