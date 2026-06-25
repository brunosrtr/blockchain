package br.com.cesurg.chainnote.core;

import br.com.cesurg.chainnote.conteudo.Conteudo;

import java.io.Serializable;

public class Bloco implements Serializable {

    private final int id;
    private final long timestamp;
    private final Conteudo dado;
    private final String hashAnterior;
    private final String hashAtual;

    public Bloco(int id, long timestamp, Conteudo dado, String hashAnterior, String hashAtual) {
        this.id = id;
        this.timestamp = timestamp;
        this.dado = dado;
        this.hashAnterior = hashAnterior;
        this.hashAtual = hashAtual;
    }

    public int getId() { return id; }
    public long getTimestamp() { return timestamp; }
    public Conteudo getDado() { return dado; }
    public String getHashAnterior() { return hashAnterior; }
    public String getHashAtual() { return hashAtual; }

    public String gerarEntradaParaHash() {
        if (id == 0) return "genesis";
        return id + "" + timestamp + dado.serializarParaHash() + hashAnterior;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + dado + " | hash: " + hashAtual;
    }
}
