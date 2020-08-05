package Resultado;

public class Resultado {
    protected short RA;
    protected int Cod;
    protected double Nota;
    protected double Frequencia;

    public Resultado(){}

    public Resultado(short ra, int cod, double nota, double frequencia) throws Exception{
        this.setRA(ra);
        this.setCod(cod);
        this.setNota(nota);
        this.setFrequencia(frequencia);
    }

    public void setRA(short ra) throws Exception{
        if(ra > 99999 || ra < 10000)
            throw new Exception("Ra inválido");
        this.RA = ra;
    }

    public void setCod(int cod) throws Exception{
        if(cod < 0)
            throw new Exception("Código inválido");
        this.Cod = cod;
    }

    public void setNota(double nota) throws Exception{
        if(nota < 0 || nota > 10)
            throw new Exception("Nota inválida");
        this.Nota = nota;
    }

    public void setFrequencia(double frequencia) throws Exception{
        if(frequencia < 0 || frequencia > 1)
            throw new Exception("Frequencia inválida");
        this.Frequencia = frequencia;
    }

    public short getRA(){return this.RA;}
    public int getCod(){return this.Cod;}
    public double getNota(){return this.Nota;}
    public double getFrequencia(){return this.Frequencia;}

    public String toString ()
    {
        String ret="[";

        ret += RA + "," + Cod + "," + Nota + "," + Frequencia;

        return ret+"]";
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        Resultado res = (Resultado)obj;

       if(this.RA != res.RA)
           return false;
       if(this.Cod != res.Cod)
           return false;
       if(this.Nota != res.Nota)
           return false;

       if (this.Frequencia != res.Frequencia)
           return false;

        return true;
    }

    public int hashCode ()
    {
        final int PRIMO = 13; // qualquer número primo serve

        int ret=666; // qualquer inteiro positivo serve

        ret = 17*ret + new Short(this.RA).hashCode();
        ret = 17*ret + new Integer(this.Cod).hashCode();
        ret = 17*ret + new Double(this.Nota).hashCode();
        ret = 17*ret + new Double(this.Frequencia).hashCode();

        if (ret<0) ret = -ret;

        return ret;
    }

}
