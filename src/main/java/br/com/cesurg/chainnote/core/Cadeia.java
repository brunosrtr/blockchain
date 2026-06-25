package br.com.cesurg.chainnote.core;

import br.com.cesurg.chainnote.conteudo.Conteudo;
import br.com.cesurg.chainnote.hash.CalculadoraHash;

import java.util.ArrayList;
import java.util.List;

public class Cadeia {

    private final List<Bloco> blocos = new ArrayList<>();
    private final CalculadoraHash calculadora;

    public Cadeia(CalculadoraHash calculadora) {
        this.calculadora = calculadora;
        adicionarBlocoGenesis();
    }

    private void adicionarBlocoGenesis() {
        String hashAtual = calculadora.calcular("genesis");
        blocos.add(new Bloco(0, System.currentTimeMillis(), null, "0", hashAtual));
    }

    public void adicionar(Conteudo dado) {
        Bloco ultimo = blocos.getLast();
        String entrada = ultimo.getId() + 1 + System.currentTimeMillis() + dado.serializarParaHash() + ultimo.getHashAtual();
        String hashAtual = calculadora.calcular(entrada);
        blocos.add(new Bloco(blocos.size(), System.currentTimeMillis(), dado, ultimo.getHashAtual(), hashAtual));
    }

    public List<Bloco> getBlocos() {
        return blocos;
    }
}
