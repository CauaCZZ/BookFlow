package br.com.bookflow;

public class Livro {
    private String titulo;
    private String genero;
    private String autor;
    private int anoDePublicacao;
    private String isbn;

    public Livro(String titulo, String genero, String autor, int anoDePublicacao, String isbn) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.anoDePublicacao = anoDePublicacao;
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public String getIsbn() {
        return isbn;
    }


}
