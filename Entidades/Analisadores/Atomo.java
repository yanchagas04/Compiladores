package Entidades.Analisadores;

public class Atomo {
    private String atomo;
    private String codigo;
    private int linha;

    public Atomo(String atomo, String codigo, int linha) {
        setAtomo(atomo);
        setCodigo(codigo);
        setLinha(linha);
    }

    public String getAtomo() {
        return atomo;
    }

    public void setAtomo(String atomo) {
        this.atomo = atomo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    
}
