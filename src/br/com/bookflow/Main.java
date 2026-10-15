package br.com.bookflow;

public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno(1, "Cauã");
        Professor professor = new Professor(2, "Claudio");
        professor.inativar();

        Livro livro = new Livro(
                "Dom Casmurro",
                "Romance",
                "Machado de Assis",
                1899,
                "9788520932599");

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.cadastrarUsuario(aluno);
        biblioteca.cadastrarUsuario(professor);
        biblioteca.cadastrarLivro(livro);

        biblioteca.emprestarLivro(aluno, livro);
        System.out.println(livro.getStatusLivro());

        biblioteca.devolverLivro(aluno, livro);
        System.out.println(livro.getStatusLivro());

        try {
            biblioteca.emprestarLivro(professor, livro);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(biblioteca.buscarLivrosPorTitulo("CASMURRO").size());
        System.out.println(biblioteca.buscarLivrosPorTitulo("quincas").size());

        System.out.println(biblioteca.buscarLivrosPorAutor("machado").size());
        System.out.println(biblioteca.buscarLivrosPorGenero("ROMANCE").size());
        System.out.println(biblioteca.buscarLivrosDisponiveis().size());
        biblioteca.emprestarLivro(aluno, livro);
        System.out.println(biblioteca.buscarLivrosDisponiveis().size());
    }
}