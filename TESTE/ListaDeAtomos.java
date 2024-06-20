
import java.util.ArrayList;

public class ListaDeAtomos {
    private ArrayList<Atomo> atomos;
    private String nome_arquivo;

    public ListaDeAtomos(String nome_arquivo){
        this.nome_arquivo = nome_arquivo;
        atomos = new ArrayList<Atomo>();
    }

    @Override
    public String toString(){
        String retorno = "";
        retorno = "Código da Equipe: 04\n";
        retorno = retorno + "Componentes:\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n";
        retorno = retorno + "\tnome; email; (71)x xxxx-xxxx\n\n";
        retorno = retorno + "RELATÓRIO DA ANÁLISE LÉXICA. Texto fonte analisado: " + nome_arquivo + "\n";
        retorno = retorno + "\n\n";
        for (Atomo atomo : atomos){
            retorno = retorno + atomo.toString() + "\n\n";
        }
        return retorno;
    }

    public void addAtomo(Atomo atomo){
        if (!(atomo.getAtomo().equals("")) && !(atomo.getAtomo().equals("\n")) && !(atomo.getAtomo().equals(" ")))
            atomos.add(atomo);
    }
}
