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
        long timestamp = System.currentTimeMillis();
        int novoId = blocos.size();
        Bloco novo = new Bloco(novoId, timestamp, dado, ultimo.getHashAtual(), "");
        String hashAtual = calculadora.calcular(novo.gerarEntradaParaHash());
        blocos.add(new Bloco(novoId, timestamp, dado, ultimo.getHashAtual(), hashAtual));
    }

    public void adulterar(int id, Conteudo novoDado) {
        Bloco original = blocos.get(id);
        blocos.set(id, new Bloco(original.getId(), original.getTimestamp(), novoDado, original.getHashAnterior(), original.getHashAtual()));
    }

    public void carregar(List<Bloco> blocosCarregados) {
        if (!blocosCarregados.isEmpty()) {
            blocos.clear();
            blocos.addAll(blocosCarregados);
        }
    }

    public List<Bloco> getBlocos() {
        return blocos;
    }
}
