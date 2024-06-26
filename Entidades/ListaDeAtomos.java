package Entidades;
import java.util.ArrayList;

import Entidades.Lexico.Atomo;

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
        retorno = retorno + "\tYan Campêlo das Chagas; yan.chagas@aln.senaicimatec.edu.br; (71) 9 9609-6720\n";
        retorno = retorno + "\tPedro Lima Almeida Santos; pedro.l.santos@aln.senaicimatec.edu.br; (71) 9 9934-7916\n";
        retorno = retorno + "\tVitor Hugo de Jesus Santos; vitor.s@aln.senaicimatec.edu.br; (71) 9 9170-5668\n";
        retorno = retorno + "\tVinicius Fernandes de Oliveira; vinicius.o@aln.senaicimatec.edu.br; (71) 9 87360997\n\n";
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
