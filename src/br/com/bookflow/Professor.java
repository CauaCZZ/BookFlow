package br.com.bookflow;

public class Professor extends Usuario{
    public Professor(int id, String nome) {
        super(id, nome);
    }

    @Override
    public int obterLimiteDeLivros() {
        return 15;
    }

    @Override
    public int obterPrazoDeEmprestimo() {
        return 30;
    }

}
