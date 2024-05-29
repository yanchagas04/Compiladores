package Entidades.Tabelas;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class TabelaReservados {
    private Dictionary<String, String> tabela;

    public TabelaReservados() {
        tabela = new Hashtable<String, String>();
        tabela.put("cadeia", "A01");
        tabela.put("caracter", "A02");
        tabela.put("declaracoes", "A03");
        tabela.put("enquanto", "A04");
        tabela.put("false", "A05");
        tabela.put("fimDeclaracoes", "A06");
        tabela.put("fimEnquanto", "A07");
        tabela.put("fimFunc", "A08");
        tabela.put("fimFuncoes", "A09");
        tabela.put("fimPrograma", "A10");
        tabela.put("fimSe", "A11");
        tabela.put("funcoes", "A12");
        tabela.put("imprime", "A13");
        tabela.put("inteiro", "A14");
        tabela.put("logico", "A15");
        tabela.put("pausa", "A16");
        tabela.put("programa", "A17");
        tabela.put("real", "A18");
        tabela.put("retorna", "A19");
        tabela.put("se", "A20");
        tabela.put("senao", "A21");
        tabela.put("tipoFunc", "A22");
        tabela.put("tipoParam", "A23");
        tabela.put("tipoVar", "A24");
        tabela.put("true", "A25");
        tabela.put("vazio", "A26");
        tabela.put("%", "B01");
        tabela.put("(", "B02");
        tabela.put(")", "B03");
        tabela.put(",", "B04");
        tabela.put(":", "B05");
        tabela.put(":=", "B06");
        tabela.put(";", "B07");
        tabela.put("?", "B08");
        tabela.put("[", "B09");
        tabela.put("]", "B10");
        tabela.put("{", "B11");
        tabela.put("}", "B12");
        tabela.put("-", "B13");
        tabela.put("*", "B14");
        tabela.put("/", "B15");
        tabela.put("+", "B16");
        tabela.put("!=", "B17");
        tabela.put("#", "B17");
        tabela.put("<", "B18");
        tabela.put("<=", "B19");
        tabela.put("==", "B20");
        tabela.put(">", "B21");
        tabela.put(">=", "B22");
    }

    public Dictionary<String, String> getTabela() {
        return tabela;
    }

    public void imprimir() {
        Enumeration codigos = tabela.elements();
        Enumeration atomos = tabela.keys();  
        // Displaying the Enumeration 
        System.out.println("  Tabela de Reservados\n");
        System.out.println("   Átomo       | Código");
        System.out.println("---------------+-------");
        while (codigos.hasMoreElements()) {
            String atomo = atomos.nextElement().toString();
            String espaco = "";
            for (int i = 0; i < 14 - atomo.length(); i++) {
                espaco = espaco + " ";
            }
            System.out.println(atomo + espaco + " | " + codigos.nextElement()); 
        }
    }
}
