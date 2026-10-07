package br.com.bookflow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void emprestarLivro(Usuario usuario, Livro livro) {
        if (livro.getStatusLivro() != StatusLivro.DISPONIVEL) {
            return;
        }

        long quantidadeEmprestimos = emprestimos.stream()
                .filter(emprestimo -> emprestimo.getUsuario() == usuario)
                .filter(emprestimo -> emprestimo.getDataDevolucao() == null)
                .count();

        if (!usuario.podeEmprestar((int) quantidadeEmprestimos)) {
            return;
        }

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(usuario.obterPrazoDeEmprestimo());

        Emprestimo emprestimo = new Emprestimo(
                usuario,
                livro,
                dataInicial,
                dataFinal
        );

        emprestimos.add(emprestimo);
        livro.emprestar();
    }

}
