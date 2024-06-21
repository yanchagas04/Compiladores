public class Atomo {
    private String atomo;
    private String codigo;
    private int linha;
    private String indiceTabSim;

    public Atomo(String atomo, String codigo, int linha, String indiceTabSim) {
        setAtomo(atomo);
        setCodigo(codigo);
        setLinha(linha);
        setIndiceTabSim(indiceTabSim);
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



    public String getIndiceTabSim() {
        return indiceTabSim;
    }



    public void setIndiceTabSim(String indiceTabSim) {
        this.indiceTabSim = indiceTabSim;
    }

    public String toString() {
        return ("Lexeme: " + atomo + ", Código: " + codigo + ", IndiceTabSim: " + indiceTabSim + ", Linha: " + linha);
    }
}
