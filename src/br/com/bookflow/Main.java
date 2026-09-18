package br.com.bookflow;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno(1, "Cauã");

        System.out.println(aluno.getNome());
        System.out.println(aluno.getId());
        System.out.println(aluno.getStatus());

        System.out.println(aluno.obterLimiteDeLivros());
        System.out.println(aluno.obterPrazoDeEmprestimo());

        Professor professor = new Professor(2,"Claudio");

        System.out.println(professor.getNome());
        System.out.println(professor.getId());
        System.out.println(professor.getStatus());

        System.out.println(professor.obterLimiteDeLivros());
        System.out.println(professor.obterPrazoDeEmprestimo());

        professor.inativar();
        System.out.println(professor.getStatus());

        Livro livro = new Livro(
                "Dom Casmurro",
                "Romance",
                "Machado de Assis",
                1899,
                "9788520932599");
        System.out.println(livro.getTitulo());
        System.out.println(livro.getGenero());
        System.out.println(livro.getAutor());
        System.out.println(livro.getAnoDePublicacao());
        System.out.println(livro.getIsbn());

        LocalDate dataInicial = LocalDate.of(2026, 9, 1);
        LocalDate dataFinal = LocalDate.of(2026, 9, 15);

        Emprestimo emprestimo = new Emprestimo(
                aluno,
                livro,
                dataInicial,
                dataFinal
        );

        emprestimo.devolver(LocalDate.of(2026, 9, 10));

        System.out.println(emprestimo.getDataInicial());
        System.out.println(emprestimo.getDataFinal());
        System.out.println(emprestimo.getDataDevolucao());
        System.out.println(emprestimo.getUsuario().getNome());
        System.out.println(emprestimo.getLivro().getTitulo());
    }
}