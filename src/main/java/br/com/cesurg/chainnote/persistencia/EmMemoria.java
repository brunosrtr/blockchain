package br.com.cesurg.chainnote.persistencia;

import br.com.cesurg.chainnote.core.Bloco;

import java.util.ArrayList;
import java.util.List;

public class EmMemoria implements RepositorioCadeia {

    private List<Bloco> blocos = new ArrayList<>();

    @Override
    public void salvar(List<Bloco> blocos) {
        this.blocos = new ArrayList<>(blocos);
    }

    @Override
    public List<Bloco> carregar() {
        return new ArrayList<>(blocos);
    }
}
