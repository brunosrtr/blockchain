package br.com.cesurg.chainnote.conteudo;

public class NotaTransacao implements Conteudo {

    private final String origem;
    private final String destino;
    private final double valor;

    public NotaTransacao(String origem, String destino, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    @Override
    public String serializarParaHash() {
        return origem + destino + valor;
    }

    @Override
    public String toString() {
        return origem + " -> " + destino + ": R$" + valor;
    }
}
