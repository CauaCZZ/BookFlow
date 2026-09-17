#  BookFlow

> Organizando livros. Simplificando o fluxo da biblioteca.



Comecei o BookFlow pensando lá na frente: quando eu chegar em Spring Boot nos estudos, quero ter uma base pronta pra aplicar o que for aprendendo, em vez de sair criando projeto do zero toda vez. Biblioteca é um domínio simples de entender  livros, usuários, empréstimos, devoluções o que deixa mais espaço pra focar na parte técnica sem ficar preso tentando entender as regras de negócio.

Por enquanto é só Java puro, construído aos poucos enquanto avanço nos estudos. A ideia é ir evoluindo o projeto junto comigo: primeiro a lógica e a estrutura básica funcionando, depois quando eu estiver lá trazer o Spring Boot pra cá.

## O que ele faz (ou vai fazer)

- Cadastro de livros
- Cadastro de usuários
- Consulta de livros
- Empréstimo
- Devolução
- Busca e filtragem

Nem tudo está pronto ainda vou marcando aqui conforme for implementando.

## Tecnologias

Java, Git e GitHub. Spring Boot deve entrar mais pra frente.

## Por que "BookFlow"?

O nome vem do fluxo do livro dentro da biblioteca:
**disponível → emprestado → devolvido → disponível de novo**

É basicamente o coração do sistema

## Evolução do projeto

### Estrutura de usuários

Comecei a modelar os usuários do sistema usando conceitos de POO. Criei a classe abstrata `Usuario`, com os atributos `id`, `nome` e `status` encapsulados, além do comportamento `inativar()`, que muda o status do usuário.

O `status` usa o enum `StatusUsuario` (`ATIVO` / `INATIVO`).

A partir de `Usuario`, criei `Aluno` e `Professor` por herança. Cada um tem suas próprias regras de limite de livros e prazo de empréstimo:

- **Aluno**: até 5 livros, 14 dias de prazo
- **Professor**: até 15 livros, 30 dias de prazo

Os métodos `obterLimiteDeLivros()` e `obterPrazoDeEmprestimo()` são abstratos em `Usuario` cada subtipo é obrigado a implementar sua própria regra.

### Estrutura de livros

Criei a classe `Livro` com 5 atributos que são características de um livro: `titulo`, `genero`, `autor`, `anoDePublicacao` e `isbn`.

Criei um construtor que contém todos esses atributos para serem inicializados. Depois disso fiz o teste na `Main` e instanciei um livro.

Como os atributos são privados, outras classes não conseguem acessá-los diretamente. Por isso, criei métodos `get` públicos para permitir a consulta desses valores.

## Status

**Em desenvolvimento, sem pressa.**

Esse README vai mudando junto com o projeto.