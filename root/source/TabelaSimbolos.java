package source;

import java.util.ArrayList;

public class TabelaSimbolos {

    private ArrayList<Simbolo> simbolos;
    private String nome_arquivo;

    public TabelaSimbolos(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
        simbolos = new ArrayList<Simbolo>();
        
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
        retorno = retorno + "RELATÓRIO DA ANÁLISE DA TABELA DE SÍMBOLOS. Texto fonte analisado: " + nome_arquivo + "\n";
        retorno = retorno + "\n\n";
        for (Simbolo simb : simbolos){
            retorno = retorno + simb.toString() + "\n\n";
        }
        return retorno;
    }

    public int proximaEntrada(){
        return simbolos.size() + 1;
    }

    public void addSimbolo(Simbolo simbolo){
        simbolos.add(simbolo);
    }

    public void addLinhaAoSimbolo(String simbolo, int linha){
        for (Simbolo simb : simbolos){
            if (simb.getLexeme().equals(simbolo)){
                if (simb.getLinhas().size() == 5)
                    return;
                simb.addLinhas(linha);
                return;
            }
        }
    }

    public boolean possui(String simbolo, String codigo){

        for (Simbolo simb : simbolos){
            if (simb.getLexeme().equals(simbolo))
                return true;
        }
        return false;
    }
}