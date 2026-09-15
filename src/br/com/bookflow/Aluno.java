package br.com.bookflow;

public class Aluno extends Usuario{
    public Aluno(int id, String nome) {
        super(id, nome);
    }

    @Override
    public int obterLimiteDeLivros() {
        return 5;
    }

    @Override
    public int obterPrazoDeEmprestimo() {
        return 14;
    }
}
