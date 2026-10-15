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

    public Emprestimo emprestarLivro(Usuario usuario, Livro livro) {
        if (!usuarios.contains(usuario)) {
            throw new IllegalArgumentException("Usuário não cadastrado na biblioteca.");
        }

        if (!livros.contains(livro)) {
            throw new IllegalArgumentException("Livro não cadastrado na biblioteca.");
        }

        if (!usuario.estaAtivo()) {
            throw new IllegalStateException("Usuário inativo não pode realizar empréstimos.");
        }

        if (livro.getStatusLivro() != StatusLivro.DISPONIVEL) {
            throw new IllegalStateException("Livro indisponível para empréstimo.");
        }

        long quantidadeEmprestimos = emprestimos.stream()
                .filter(emprestimo -> emprestimo.getUsuario() == usuario)
                .filter(emprestimo -> emprestimo.getDataDevolucao() == null)
                .count();

        if (!usuario.podeEmprestar((int) quantidadeEmprestimos)) {
            throw new IllegalStateException("Usuário atingiu o limite de empréstimos.");
        }

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(usuario.obterPrazoDeEmprestimo());

        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataInicial, dataFinal);

        emprestimos.add(emprestimo);
        livro.emprestar();

        return emprestimo;
    }

    public void devolverLivro(Usuario usuario, Livro livro) {
        Emprestimo emprestimo = emprestimos.stream()
                .filter(e -> e.getUsuario() == usuario)
                .filter(e -> e.getLivro() == livro)
                .filter(e -> e.getDataDevolucao() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Não há empréstimo ativo deste livro para este usuário."));

        emprestimo.devolver(LocalDate.now());
        livro.devolver();
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    public List<Livro> buscarLivrosPorGenero(String genero) {
        return livros.stream()
                .filter(livro -> livro.getGenero().toLowerCase().contains(genero.toLowerCase()))
                .toList();
    }

    public List<Livro> buscarLivrosDisponiveis() {
        return livros.stream()
                .filter(livro -> livro.getStatusLivro() == StatusLivro.DISPONIVEL)
                .toList();
    }

}