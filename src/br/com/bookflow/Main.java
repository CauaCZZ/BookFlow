package br.com.bookflow;

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
    }
}