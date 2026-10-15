# BookFlow

> Organizando livros. Simplificando o fluxo da biblioteca.

Comecei o BookFlow pensando lá na frente: quando eu chegar em Spring Boot nos estudos, quero ter uma base pronta pra aplicar o que for aprendendo, em vez de sair criando projeto do zero toda vez. Biblioteca é um domínio simples de entender (livros, usuários, empréstimos e devoluções), o que deixa mais espaço pra focar na parte técnica sem ficar preso tentando entender as regras de negócio.

Por enquanto é só Java puro, construído aos poucos enquanto avanço nos estudos. A ideia é ir evoluindo o projeto junto comigo: primeiro a lógica e a estrutura básica funcionando, depois, quando eu estiver lá, trazer o Spring Boot pra cá.

## O que já funciona

* Cadastro de livros e usuários
* Empréstimo, com validação das regras
* Devolução
* Busca de livros por título, autor, gênero e disponibilidade
* Consulta do acervo

## Tecnologias

Java, Collections, Streams, Git e GitHub. Spring Boot deve entrar mais pra frente.

## Por que "BookFlow"?

O nome vem do fluxo do livro dentro da biblioteca:
**disponível → emprestado → devolvido → disponível de novo**

É basicamente o coração do sistema, e hoje esse ciclo já funciona de ponta a ponta.

## Evolução do projeto

### Estrutura de usuários

Comecei a modelar os usuários do sistema usando conceitos de POO. Criei a classe abstrata `Usuario`, com os atributos `id`, `nome` e `status` encapsulados, além do comportamento `inativar()`, que muda o status do usuário.

O `status` usa o enum `StatusUsuario` (`ATIVO` / `INATIVO`).

A partir de `Usuario`, criei `Aluno` e `Professor` por herança. Cada um tem suas próprias regras de limite de livros e prazo de empréstimo:

* **Aluno**: até 5 livros, 14 dias de prazo
* **Professor**: até 15 livros, 30 dias de prazo

Os métodos `obterLimiteDeLivros()` e `obterPrazoDeEmprestimo()` são abstratos em `Usuario`. Cada subtipo é obrigado a implementar sua própria regra.

Usei classe abstrata e não interface porque `Usuario` guarda estado (`id`, `nome`, `status`) e já tem comportamento pronto (`inativar()`) que `Aluno` e `Professor` aproveitam. Uma interface não guarda estado, então cada subclasse teria que repetir tudo isso.

### Estrutura de livros

Criei a classe `Livro` com 5 atributos que são características de um livro: `titulo`, `genero`, `autor`, `anoDePublicacao` e `isbn`.

Criei um construtor que contém todos esses atributos para serem inicializados. Depois disso fiz o teste na `Main` e instanciei um livro.

Como os atributos são privados, outras classes não conseguem acessá-los diretamente. Por isso, criei métodos `get` públicos para permitir a consulta desses valores.

### Estrutura de empréstimos

Criei a classe `Emprestimo` com os atributos `usuario`, `livro`, `dataInicial`, `dataFinal` e `dataDevolucao`.

No caso de `usuario` e `livro`, fiz uma associação com as classes `Usuario` e `Livro`. Dessa forma, o `Emprestimo` mantém uma referência para o usuário e para o livro envolvidos no empréstimo, permitindo acessar seus dados por meio dos métodos dessas classes.

Para trabalhar com as datas, utilizei a API `java.time` do Java e a classe `LocalDate` para representar as datas do empréstimo e da devolução.

Criei o construtor com todos os atributos, menos `dataDevolucao`, pois não sabemos quando a devolução será efetuada. Sendo assim, criei o método `devolver(LocalDate dataDevolucao)` para registrar a data em que o livro foi devolvido.

Também criei getters para consultar os atributos do empréstimo.

### Status do livro e regras de negócio

Adicionei o enum `StatusLivro` (`DISPONIVEL` / `EMPRESTADO`). Todo livro nasce `DISPONIVEL`, e os métodos `emprestar()` e `devolver()` da própria classe `Livro` controlam a mudança de estado.

Na classe `Usuario`, criei `podeEmprestar()`, que compara a quantidade de empréstimos ativos com o limite do tipo de usuário, e `estaAtivo()`, que diz se o usuário pode usar a biblioteca.

Deixei essas regras dentro de `Livro` e `Usuario` porque segui a ideia de que cada classe cuida do que sabe sobre si mesma. O livro sabe se está disponível e o usuário sabe qual é o seu limite. Assim, a regra fica em um lugar só e ninguém mexe no estado de outra classe por fora.

### Estrutura da biblioteca

Criei a classe `Biblioteca` para centralizar as operações do sistema. Ela mantém listas de `Livro`, `Usuario` e `Emprestimo`, e tem os métodos `cadastrarLivro()`, `cadastrarUsuario()`, `emprestarLivro()`, `devolverLivro()`, `listarLivros()` e as buscas `buscarLivrosPorTitulo()`, `buscarLivrosPorAutor()`, `buscarLivrosPorGenero()` e `buscarLivrosDisponiveis()`.

A `Biblioteca` só orquestra: ela pergunta pro `Usuario` e pro `Livro` se a operação é permitida, em vez de guardar as regras dentro dela. Se uma regra mudar, mexo só na classe dona dela.

Antes de emprestar, a biblioteca confere nesta ordem: se usuário e livro estão cadastrados, se o usuário está ativo, se o livro está disponível e se o usuário não passou do limite. Pra contar os empréstimos ativos, usei `Stream` com `filter()` e `count()`. Se tudo estiver certo, cria o `Emprestimo`, calcula a data final com base no prazo do usuário e muda o status do livro para `EMPRESTADO`.

Escolhi exceções em vez de retornar `true`/`false`. Na primeira versão, o `emprestarLivro()` simplesmente saía do método quando algo dava errado, e quem chamava não tinha como saber o que aconteceu. Um `boolean` resolveria só metade, porque diria que falhou sem dizer o motivo. Com exceções, cada falha carrega uma mensagem própria (livro indisponível, usuário inativo, limite atingido). Usei `IllegalArgumentException` para argumento inválido (algo que nunca foi cadastrado) e `IllegalStateException` para quando o estado do objeto não permite a ação.

O `devolverLivro()` procura o empréstimo ativo daquele usuário para aquele livro, registra a data de devolução e volta o livro para `DISPONIVEL`. Foi com ele que o ciclo do BookFlow passou a funcionar completo.

O `buscarLivrosPorTitulo()` percorre o acervo com `Stream` e devolve uma lista com os livros cujo título contém o texto buscado, sem diferenciar maiúsculas de minúsculas. Se nenhum livro bater, devolve uma lista vazia em vez de `null`, pra quem chama poder percorrer o resultado direto, sem checar nada antes.

Usei "contém" em vez de "igual" porque quem busca "casmurro" quer achar "Dom Casmurro", sem precisar lembrar o título inteiro. O custo é que a busca por "dom" também acha "Freedom". Achei esse custo aceitável: mostrar um resultado a mais incomoda pouco, mas esconder um livro que existe faz a pessoa achar que ele não está na biblioteca.

As buscas por autor e por gênero seguem a mesma lógica da de título. A busca por disponíveis é diferente: não recebe parâmetro, porque o critério é sempre o `StatusLivro.DISPONIVEL`, e responde à pergunta "o que dá pra pegar agora?".

Todas as buscas ficam na `Biblioteca` porque só ela enxerga a lista de livros (que é privada). Uma classe nova teria que receber essa lista de fora, e isso quebraria o encapsulamento.

Ficaram de fora as buscas por ISBN e por ano. O ISBN identifica um livro só, então pede comparação exata e outro tipo de retorno (um livro, não uma lista). O ano é um número, e o `contains()` não se aplica a ele. Os dois ficam pra uma próxima etapa.

O `listarLivros()` devolve o acervo inteiro, mas dentro de uma lista que não aceita alteração (`Collections.unmodifiableList`). Se eu devolvesse a própria lista `livros`, qualquer classe de fora poderia chamar `clear()` ou `add()` nela e mexer no acervo sem passar pela `Biblioteca`, mesmo com o campo sendo privado. Com a lista protegida, quem tenta alterar recebe um `UnsupportedOperationException`. Preferi isso a devolver uma cópia porque a cópia não avisa nada (a alteração se perde em silêncio), ocupa memória a cada consulta e fica desatualizada se o acervo mudar depois.

### Limitações conhecidas

Hoje as comparações de usuário e livro usam `==` e `contains()`, que comparam se é o mesmo objeto na memória. Funciona enquanto uso sempre as mesmas instâncias, mas não reconheceria dois objetos diferentes com o mesmo `id`. A solução é implementar `equals()` e `hashCode()`, que fica pra uma próxima etapa.

A busca por título também não tolera erro de digitação: "casmuro" com um "r" só não acha "Dom Casmurro". Isso exigiria busca aproximada, que é mais complexa e fica pra depois.

Os quatro métodos de busca repetem o mesmo começo (`livros.stream().filter(...).toList()`), mudando só a condição. Deixei assim de propósito, por ser simples de ler no tamanho atual do projeto. Quando eu estudar uma forma de passar a condição como parâmetro, volto aqui pra reduzir essa repetição.

A lista devolvida por `listarLivros()` não pode ser alterada, mas os livros dentro dela são os objetos reais. Quem consulta ainda consegue chamar `livro.emprestar()` direto, sem passar pela `Biblioteca`. Pro tamanho atual do projeto aceitei isso.

## Status

**Em desenvolvimento, sem pressa.**

Esse README vai mudando junto com o projeto.