package br.com.bookflow;

public abstract class Usuario {
    private int id;
    private String nome;
    private StatusUsuario status;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.status = StatusUsuario.ATIVO;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void inativar() {
        this.status = StatusUsuario.INATIVO;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public abstract int obterLimiteDeLivros();
    public abstract int obterPrazoDeEmprestimo();
}
