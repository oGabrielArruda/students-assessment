package MensagemResultado;

public class MensagemResultado {
    private String mensagem;

    public MensagemResultado() {}

    public MensagemResultado(String mensagem) throws Exception {
        this.setMensagem(mensagem);
    }

    public void setMensagem(String mensagem) throws Exception {
        if(mensagem == null)
            throw new Exception("Mensagem inválida");
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(this.getClass() != obj.getClass())
            return false;

        MensagemResultado objMsg = (MensagemResultado) obj;
        if(!this.mensagem.equals(objMsg.mensagem))
            return false;
        return true;
    }

    public String toString() {
        String ret = "A mensagem do resultado é " + this.mensagem;
        return ret;
    }

    public int hashCode()
    {
        int ret = 345;
        ret = ret*17+new String(this.mensagem).hashCode();
        if(ret < 0)
            ret = -ret;
        return ret;
    }
}
