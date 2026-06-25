# ChainNote

Mini-blockchain em Java onde cada anotação se torna um bloco encadeado pelo hash do anterior.

## Como compilar e executar

**Pré-requisito:** Java 24 e Maven instalados.

```bash
# Compilar
mvn compile

# Executar
mvn exec:java -Dexec.mainClass="br.com.cesurg.chainnote.cli.App"
```

## Como funciona

Cada bloco guarda um dado (texto, imagem ou transação) e o hash do bloco anterior.
Qualquer tentativa de alterar um dado muda o hash esperado, que não bate mais com o hash armazenado — a adulteração é detectada e rejeitada na hora.

```
Bloco 0 (genesis)         Bloco 1                   Bloco 2
hashAnterior: "0"         hashAnterior: "a4f8..."    hashAnterior: "9c2d..."
hashAtual:    "a4f8..."   hashAtual:    "9c2d..."    hashAtual:    "7b1e..."
```

## Decisões de design — SOLID

### S — Single Responsibility Principle

Cada classe tem uma única razão para mudar:

| Classe | Responsabilidade |
|--------|-----------------|
| `Bloco` | Guardar dados e gerar a entrada para o hash |
| `Cadeia` | Orquestrar inserção dos blocos |
| `ValidadorCadeia` | Verificar a integridade da cadeia |
| `CalculadoraHash` | Calcular o hash de uma string |
| `RepositorioCadeia` | Salvar e carregar a cadeia |

### O — Open/Closed Principle

Para adicionar um novo algoritmo de hash basta criar uma nova classe — `SHA256Hash`, `MD5Hash` — sem alterar `Bloco`, `Cadeia` ou `ValidadorCadeia`.

O mesmo vale para novos tipos de conteúdo: `NotaTexto`, `NotaImagem` e `NotaTransacao` foram adicionados sem modificar nenhuma classe existente.

### I — Interface Segregation Principle

Três interfaces pequenas e coesas, cada uma com o mínimo necessário:

- `Conteudo` → `serializarParaHash()`
- `CalculadoraHash` → `calcular(String entrada)`
- `RepositorioCadeia` → `salvar()` e `carregar()`

Nenhuma classe é obrigada a implementar métodos que não usa.

### D — Dependency Inversion Principle

`Cadeia` e `ValidadorCadeia` dependem da interface `CalculadoraHash`, não de uma implementação concreta. A implementação é injetada pelo construtor:

```java
// Produção
new Cadeia(new SHA256Hash());

// Troca de algoritmo sem alterar Cadeia
new Cadeia(new MD5Hash());
```

O mesmo vale para `RepositorioCadeia`: `EmMemoria` e `EmArquivoJson` são intercambiáveis sem alterar nenhuma outra classe.
