import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;
import Entidades.Tabelas.Simbolo;

public class StaticChecker {
    public static void main(String[] args) {
        TabelaReservados tabela = new TabelaReservados();
        tabela.imprimir();
        TabelaSimbolos tabelaS = new TabelaSimbolos();
        
        System.out.println(tabelaS);
    }
}
