package br.com.cesurg.chainnote.cli;

import br.com.cesurg.chainnote.conteudo.NotaTexto;
import br.com.cesurg.chainnote.conteudo.NotaImagem;
import br.com.cesurg.chainnote.conteudo.NotaTransacao;
import br.com.cesurg.chainnote.core.Cadeia;
import br.com.cesurg.chainnote.hash.SHA256Hash;
import br.com.cesurg.chainnote.persistencia.EmArquivoJson;
import br.com.cesurg.chainnote.persistencia.RepositorioCadeia;
import br.com.cesurg.chainnote.validacao.ValidadorCadeia;

import java.util.Scanner;

public class App {

    static Cadeia cadeia = new Cadeia(new SHA256Hash());
    static ValidadorCadeia validador = new ValidadorCadeia(new SHA256Hash());
    static RepositorioCadeia repositorio = new EmArquivoJson("cadeia.json");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== ChainNote ===");
            System.out.println("1. Adicionar nota de texto");
            System.out.println("2. Adicionar nota de imagem");
            System.out.println("3. Adicionar transacao");
            System.out.println("4. Listar cadeia");
            System.out.println("5. Salvar cadeia");
            System.out.println("6. Carregar cadeia");
            System.out.println("7. Adulterar bloco");
            System.out.println("0. Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> adicionarTexto();
                case 2 -> adicionarImagem();
                case 3 -> adicionarTransacao();
                case 4 -> listar();
                case 5 -> salvar();
                case 6 -> carregar();
                case 7 -> adulterar();
            }
        } while (opcao != 0);
    }

    static void adicionarTexto() {
        System.out.print("Texto: ");
        cadeia.adicionar(new NotaTexto(scanner.nextLine()));
        System.out.println("Bloco adicionado.");
    }

    static void adicionarImagem() {
        System.out.print("Caminho da imagem: ");
        cadeia.adicionar(new NotaImagem(scanner.nextLine()));
        System.out.println("Bloco adicionado.");
    }

    static void adicionarTransacao() {
        System.out.print("Origem: ");
        String origem = scanner.nextLine();
        System.out.print("Destino: ");
        String destino = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());
        cadeia.adicionar(new NotaTransacao(origem, destino, valor));
        System.out.println("Bloco adicionado.");
    }

    static void listar() {
        cadeia.getBlocos().forEach(System.out::println);
    }

    static void salvar() {
        repositorio.salvar(cadeia.getBlocos());
        System.out.println("Cadeia salva.");
    }

    static void carregar() {
        cadeia.carregar(repositorio.carregar());
        System.out.println("Cadeia carregada.");
    }

    static void adulterar() {
        System.out.print("ID do bloco a adulterar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Novo texto: ");

        var dadoOriginal = cadeia.getBlocos().get(id).getDado();
        cadeia.adulterar(id, new NotaTexto(scanner.nextLine()));

        if (!validador.validar(cadeia.getBlocos())) {
            cadeia.adulterar(id, dadoOriginal);
            System.out.println("Adulteracao detectada! Alteracao rejeitada.");
        } else {
            System.out.println("Bloco alterado.");
        }
    }
}
