package source;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AnalisadorSintatico {

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

    public static String pegarPastaDoArquivo(File arquivo){
        return arquivo.getParentFile().getAbsolutePath();
    }

    public static void gerarRelatorios(File arquivo, TabelaSimbolos tabSim, ListaDeAtomos atomos) throws IOException {
        String base_path = "", nome = arquivo.getName();
        nome = nome.substring(0, nome.length() - 4);
        if (arquivo.getParent() != null)
            base_path = arquivo.getParent() + "/";

        FileWriter escritorLEX = new FileWriter(base_path + nome + ".LEX");
        BufferedWriter bwriterLEX = new BufferedWriter(escritorLEX);
        bwriterLEX.write(atomos.toString());

        FileWriter escritorTAB = new FileWriter(base_path + nome + ".TAB");
        BufferedWriter bwriterTAB = new BufferedWriter(escritorTAB);
        bwriterTAB.write(tabSim.toString());

        bwriterLEX.close();
        bwriterTAB.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o nome/caminho do arquivo a ser analisado: ");
        String nome_arquivo = sc.nextLine();
        if (!nome_arquivo.endsWith(".241"))
            nome_arquivo = nome_arquivo + ".241";
        try {
            File arquivo = new File(nome_arquivo);
            TabelaReservados tabRes = new TabelaReservados();
            TabelaSimbolos tabSim = new TabelaSimbolos(nome_arquivo);
            ListaDeAtomos atomos = new ListaDeAtomos(nome_arquivo);
            Scanner leitor = new Scanner(arquivo);
            leitor.useDelimiter("");   
            AnalisadorLexico lexico = new AnalisadorLexico(tabRes, tabSim, Escopo.variavel, leitor);
            while (true){
                Atomo atomo = lexico.formarAtomo();
                if (atomo == null) break;
                else {
                    lexico.setEscopo(mudarEscopo(atomo.getCodigo(), lexico.getEscopo()));
                    atomos.addAtomo(atomo);
                }
            }
            gerarRelatorios(arquivo, tabSim, atomos);
            leitor.close();
            System.out.println("Arquivo analisado com sucesso! Relatórios .LEX e .TAB montados no mesmo diretório.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao existe!");
        }
        sc.close();
    }
}
