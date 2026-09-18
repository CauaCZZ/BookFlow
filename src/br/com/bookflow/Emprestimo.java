package br.com.bookflow;

import java.time.LocalDate;

public class Emprestimo {
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private LocalDate dataDevolucao;

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataInicial, LocalDate dataFinal) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void devolver(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
