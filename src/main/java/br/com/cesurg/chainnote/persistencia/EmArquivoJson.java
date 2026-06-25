package br.com.cesurg.chainnote.persistencia;

import br.com.cesurg.chainnote.core.Bloco;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmArquivoJson implements RepositorioCadeia {

    private final String arquivo;

    public EmArquivoJson(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(List<Bloco> blocos) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(blocos);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar cadeia", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Bloco> carregar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Bloco>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
