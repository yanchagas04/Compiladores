import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;
import Entidades.Tabelas.Simbolo;

public class StaticChecker {
    public static void main(String[] args) {
        TabelaReservados tabela = new TabelaReservados();
        tabela.imprimir();
        TabelaSimbolos tabelaS = new TabelaSimbolos();
        tabelaS.addSimbolo(new Simbolo(1, "513", "Var1", 4, 4, "-", 1));
        tabelaS.addSimbolo(new Simbolo(2, "513", "Var2", 4, 4, "-", 1));
        System.out.println(tabelaS);
    }
}
