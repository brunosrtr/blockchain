package br.com.cesurg.chainnote.validacao;

import br.com.cesurg.chainnote.core.Bloco;
import br.com.cesurg.chainnote.hash.CalculadoraHash;

import java.util.List;

public class ValidadorCadeia {

    private final CalculadoraHash calculadora;

    public ValidadorCadeia(CalculadoraHash calculadora) {
        this.calculadora = calculadora;
    }

    public boolean validar(List<Bloco> blocos) {
        for (int i = 1; i < blocos.size(); i++) {
            Bloco atual = blocos.get(i);
            Bloco anterior = blocos.get(i - 1);

            if (!atual.getHashAnterior().equals(anterior.getHashAtual())) {
                System.out.println("Cadeia quebrada no bloco " + i + ": hashAnterior não bate.");
                return false;
            }

            String hashEsperado = calculadora.calcular(atual.gerarEntradaParaHash());
            if (!atual.getHashAtual().equals(hashEsperado)) {
                System.out.println("Cadeia quebrada no bloco " + i + ": hash adulterado.");
                return false;
            }
        }
        return true;
    }
}
