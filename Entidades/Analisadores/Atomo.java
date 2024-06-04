package Entidades.Analisadores;

public class Atomo {
    private String atomo;
    private String codigo;

    public Atomo(String atomo, String codigo){
        setAtomo(atomo);
        setCodigo(codigo);
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

    
}
