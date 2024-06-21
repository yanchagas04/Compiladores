import java.util.ArrayList;

public class Simbolo {
    private int entrada;
    private String codigo;
    private String lexeme;
    private int qntCharAntesTrunc;
    private int qntCharDepoisTrunc;
    private String tipoSimbolo;
    private ArrayList<Integer> linhas;

    public Simbolo(int entrada, String codigo, String lexeme, int qntCharAntesTrunc, int qntCharDepoisTrunc, String tipoSimbolo, int linha) {
        setEntrada(entrada);
        setCodigo(codigo);
        setLexeme(lexeme);
        setQntCharAntesTrunc(qntCharAntesTrunc);
        setQntCharDepoisTrunc(qntCharDepoisTrunc);
        setTipoSimbolo(tipoSimbolo);
        this.linhas = new ArrayList<Integer>();
        addLinhas(linha);
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getQntCharAntesTrunc() {
        return qntCharAntesTrunc;
    }

    public void setQntCharAntesTrunc(int qntCharAntesTrunc) {
        this.qntCharAntesTrunc = qntCharAntesTrunc;
    }

    public int getQntCharDepoisTrunc() {
        return qntCharDepoisTrunc;
    }

    public void setQntCharDepoisTrunc(int qntCharDepoisTrunc) {
        this.qntCharDepoisTrunc = qntCharDepoisTrunc;
    }

    public String getTipoSimbolo() {
        return tipoSimbolo;
    }

    public void setTipoSimbolo(String tipoSimbolo) {
        this.tipoSimbolo = tipoSimbolo;
    }

    public ArrayList<Integer> getLinhas() {
        return linhas;
    }

    public void addLinhas(int linha) {
        this.linhas.add(linha);
    }

    public String toString(){
        return ("Entrada: " + entrada + ", Código: " + codigo + ", Lexeme: " + lexeme + ", QntCharAntesTrunc: " + qntCharAntesTrunc + ", QntCharDepoisTrunc: " + qntCharDepoisTrunc + ", TipoSimbolo: " + tipoSimbolo + ", Linhas = " + linhas);
    }        
}
