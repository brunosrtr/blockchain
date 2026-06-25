package br.com.cesurg.chainnote.persistencia;

import br.com.cesurg.chainnote.core.Bloco;

import java.util.List;

public interface RepositorioCadeia {
    void salvar(List<Bloco> blocos);
    List<Bloco> carregar();
}
