import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class TabelaReservados {
    private Dictionary<String, String> tabela;

    public TabelaReservados() {
        tabela = new Hashtable<String, String>();
        tabela.put("cadeia".toUpperCase(), "A01");
        tabela.put("caracter".toUpperCase(), "A02");
        tabela.put("declaracoes".toUpperCase(), "A03");
        tabela.put("enquanto".toUpperCase(), "A04");
        tabela.put("false".toUpperCase(), "A05");
        tabela.put("fimDeclaracoes".toUpperCase(), "A06");
        tabela.put("fimEnquanto".toUpperCase(), "A07");
        tabela.put("fimFunc".toUpperCase(), "A08");
        tabela.put("fimFuncoes".toUpperCase(), "A09");
        tabela.put("fimPrograma".toUpperCase(), "A10");
        tabela.put("fimSe".toUpperCase(), "A11");
        tabela.put("funcoes".toUpperCase(), "A12");
        tabela.put("imprime".toUpperCase(), "A13");
        tabela.put("inteiro".toUpperCase(), "A14");
        tabela.put("logico".toUpperCase(), "A15");
        tabela.put("pausa".toUpperCase(), "A16");
        tabela.put("programa".toUpperCase(), "A17");
        tabela.put("real".toUpperCase(), "A18");
        tabela.put("retorna".toUpperCase(), "A19");
        tabela.put("se".toUpperCase(), "A20");
        tabela.put("senao".toUpperCase(), "A21");
        tabela.put("tipoFunc".toUpperCase(), "A22");
        tabela.put("tipoParam".toUpperCase(), "A23");
        tabela.put("tipoVar".toUpperCase(), "A24");
        tabela.put("true".toUpperCase(), "A25");
        tabela.put("vazio".toUpperCase(), "A26");
        tabela.put("%".toUpperCase(), "B01");
        tabela.put("(".toUpperCase(), "B02");
        tabela.put(")".toUpperCase(), "B03");
        tabela.put(",".toUpperCase(), "B04");
        tabela.put(":".toUpperCase(), "B05");
        tabela.put(":=".toUpperCase(), "B06");
        tabela.put(";".toUpperCase(), "B07");
        tabela.put("?".toUpperCase(), "B08");
        tabela.put("[".toUpperCase(), "B09");
        tabela.put("]".toUpperCase(), "B10");
        tabela.put("{".toUpperCase(), "B11");
        tabela.put("}".toUpperCase(), "B12");
        tabela.put("-".toUpperCase(), "B13");
        tabela.put("*".toUpperCase(), "B14");
        tabela.put("/".toUpperCase(), "B15");
        tabela.put("+".toUpperCase(), "B16");
        tabela.put("!=".toUpperCase(), "B17");
        tabela.put("#".toUpperCase(), "B17");
        tabela.put("<".toUpperCase(), "B18");
        tabela.put("<=".toUpperCase(), "B19");
        tabela.put("==".toUpperCase(), "B20");
        tabela.put(">".toUpperCase(), "B21");
        tabela.put(">=".toUpperCase(), "B22");
    }

    public Dictionary<String, String> getTabela() {
        return tabela;
    }

    public boolean possui(String atomo) {
        Enumeration <String> atomos = tabela.keys();
        while (atomos.hasMoreElements()) {
            if (atomos.nextElement().toString().equals(atomo)) {
                return true;
            }
        }
        return false;
    }

    public void imprimir() {
        Enumeration<String> codigos = tabela.elements();
        Enumeration<String> atomos = tabela.keys();  
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
