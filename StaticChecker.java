import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Entidades.Analisadores.AnalisadorLexico;
import Entidades.Analisadores.Atomo;
import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;
import Entidades.Analisadores.Escopo;

public class StaticChecker {

    public static Escopo mudarEscopo(String codigo, Escopo escopo_atual) {
        if (codigo == null)
            return Escopo.variavel;
        if (codigo.equals("A17"))
            return Escopo.nomPrograma;
        if (codigo.equals("A22")){
            return Escopo.antesNomeFunc;
        }
        if (escopo_atual == Escopo.antesNomeFunc && codigo.equals("B05")){
            return Escopo.nomFuncao;
        }
        if (escopo_atual == Escopo.antesNomeFunc){
            return Escopo.antesNomeFunc;
        }
        return Escopo.variavel;
            
    }

    public static void main(String[] args) throws FileNotFoundException {
        File arquivo = new File(args[0]);
        TabelaReservados tabRes = new TabelaReservados();
        TabelaSimbolos tabSim = new TabelaSimbolos();
        Scanner leitor = new Scanner(arquivo);
        leitor.useDelimiter("");   
        AnalisadorLexico lexico = new AnalisadorLexico(tabRes, tabSim, Escopo.variavel, leitor);
        while (true){
            Atomo atomo = lexico.formarAtomo();
            if (atomo == null) break;
            else {
                lexico.setEscopo(mudarEscopo(atomo.getCodigo(), lexico.getEscopo()));
            }
        }
        
        tabRes.imprimir();
        System.out.println(tabSim);
        System.out.println(lexico.getLinha());
    }
}
