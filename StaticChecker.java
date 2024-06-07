import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Entidades.Analisadores.AnalisadorLexico;
import Entidades.Tabelas.TabelaReservados;
import Entidades.Tabelas.TabelaSimbolos;
import Entidades.Analisadores.Escopo;

public class StaticChecker {
    public static void main(String[] args) throws FileNotFoundException {
        File arquivo = new File(args[0]);
        TabelaReservados tabRes = new TabelaReservados();
        TabelaSimbolos tabSim = new TabelaSimbolos();
        Scanner leitor = new Scanner(arquivo);
        leitor.useDelimiter("");   
        AnalisadorLexico analisador = new AnalisadorLexico(tabRes, tabSim, Escopo.variavel, leitor, 1);
        // while (true){
        //     if (analisador.formarAtomo() == null) break;
        // }
        analisador.formarAtomo();
        analisador.formarAtomo();
        analisador.formarAtomo();
        analisador.formarAtomo();
        analisador.formarAtomo();
        tabRes.imprimir();
        System.out.println(tabSim);
        
    }
}
