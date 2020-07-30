package Aluno;

public class Aluno {
    public short RA;
    public String Nome;

    public void setRA(short ra) throws Exception {
        if(ra+"".length() != 5)
            throw new Exception("Ra inválido");
        this.RA = ra;
    }
    public void setNome(String nome) throws Exception {
        if(nome == null)
            throw new Exception("Nome inválido");
        this.Nome = nome;
    }

    public Aluno(){};

    public Aluno(short ra, String nome) throws Exception
    {
        this.setRA(ra);
        this.setNome(nome);
    }

    public short getRA() {
        return this.RA;
    }

    public String getNome() {
        return this.Nome;
    }
}
