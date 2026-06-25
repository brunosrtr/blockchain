package br.com.cesurg.chainnote.persistencia;

import br.com.cesurg.chainnote.conteudo.Conteudo;
import br.com.cesurg.chainnote.conteudo.NotaImagem;
import br.com.cesurg.chainnote.conteudo.NotaTexto;
import br.com.cesurg.chainnote.conteudo.NotaTransacao;
import br.com.cesurg.chainnote.core.Bloco;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmArquivoJson implements RepositorioCadeia {

    private static final Map<String, Class<? extends Conteudo>> TIPOS = Map.of(
            "NotaTexto", NotaTexto.class,
            "NotaImagem", NotaImagem.class,
            "NotaTransacao", NotaTransacao.class
    );

    private static final Map<Class<? extends Conteudo>, String> NOMES = Map.of(
            NotaTexto.class, "NotaTexto",
            NotaImagem.class, "NotaImagem",
            NotaTransacao.class, "NotaTransacao"
    );

    private static final Gson PLAIN = new Gson();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Conteudo.class, new ConteudoAdapter())
            .setPrettyPrinting()
            .create();

    private final String arquivo;

    public EmArquivoJson(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(List<Bloco> blocos) {
        try (Writer writer = new FileWriter(arquivo)) {
            GSON.toJson(blocos, new TypeToken<List<Bloco>>() {}.getType(), writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar cadeia", e);
        }
    }

    @Override
    public List<Bloco> carregar() {
        try (Reader reader = new FileReader(arquivo)) {
            List<Bloco> blocos = GSON.fromJson(reader, new TypeToken<List<Bloco>>() {}.getType());
            return blocos != null ? blocos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static class ConteudoAdapter implements JsonSerializer<Conteudo>, JsonDeserializer<Conteudo> {

        @Override
        public JsonElement serialize(Conteudo src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = PLAIN.toJsonTree(src, src.getClass()).getAsJsonObject();
            obj.addProperty("tipo", NOMES.get(src.getClass()));
            return obj;
        }

        @Override
        public Conteudo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String tipo = obj.get("tipo").getAsString();
            Class<? extends Conteudo> classe = TIPOS.get(tipo);
            if (classe == null) throw new JsonParseException("Tipo desconhecido: " + tipo);
            return PLAIN.fromJson(json, classe);
        }
    }
}
