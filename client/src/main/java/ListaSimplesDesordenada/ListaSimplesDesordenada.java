package ListaSimplesDesordenada;

import java.lang.reflect.Method;

public class ListaSimplesDesordenada<X> {
    private class No
    {
        private X info;
        private No prox;

        public No (X info, No prox) {
            this.info = info;
            this.prox = prox;
        }

        public No (X info) {
            this.info = info;
        }

        public void setInfo(X info) {
            this.info = info;
        }

        public void setProx(No prox) {
            this.prox = prox;
        }

        public X getInfo() {
            return this.info;
        }

        public No getProx() {
            return this.prox;
        }
    }

    private No primeiro, ultimo;

    public ListaSimplesDesordenada() {
        this.primeiro = this.ultimo = null;
    }

    private X tryGetClone(X info) {
        if(info instanceof Cloneable)
            return meuCloneDeX(info);
        return info;
    }

    private X meuCloneDeX(X x) {
        X ret = null;
        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tiposDosParms = null; // null pq clone nao tem parametros
            Method metodo = classe.getMethod("clone",tiposDosParms);
            Object[] parms = null; // null pq clone nao tem parametros
            ret = (X)metodo.invoke(x,parms);
        }
        catch (Exception erro)
        {}
        return ret;
    }

    public void insiraNoInicio(X info) throws Exception {
        if(info == null)
            throw new Exception("Valor nulo inválido!");
        X inserir = tryGetClone(info);

        this.primeiro = new No(inserir, this.primeiro);
        if(this.ultimo == null)
            this.ultimo = this.primeiro;
    }

    public void insiraNoFim(X info) throws Exception {
        if(info == null)
            throw new Exception("Valor nulo inválido!");

        X inserir = tryGetClone(info);

        if(this.primeiro == null) {
            this.ultimo = new No(inserir);
            this.primeiro = this.ultimo;
            return;
        }

        this.ultimo.setProx(new No(inserir));
        this.ultimo = this.ultimo.getProx();
    }

    public void removaDoInicio() throws Exception
    {
        if(this.primeiro == null)
            throw new Exception("Lista vazia!");

        if(this.primeiro == this.ultimo) {
            this.primeiro = null;
            this.ultimo = null;
            return;
        }

        this.primeiro = this.primeiro.getProx();
    }

    public void removaDoFim() throws Exception
    {
        if(this.primeiro == null)
            throw new Exception("Lista vazia!");

        if(this.primeiro == this.ultimo) {
            this.primeiro = null;
            this.ultimo = null;
            return;
        }

        No atual = this.primeiro;
        while(atual.getProx() != this.ultimo)
            atual = atual.getProx();
        atual.setProx(null);
        this.ultimo = atual;
    }

    public boolean isVazia()
    {
        return this.primeiro == null;
    }

    public X getDoInicio() throws Exception
    {
        if(this.primeiro == null)
            throw new Exception("Lista vazia!");
        X ret = tryGetClone(this.primeiro.getInfo());
        return ret;
    }

    public X getDoFim() throws Exception
    {
        if(this.primeiro == null)
            throw new Exception("Lista vazia");
        X ret = tryGetClone(this.ultimo.getInfo());
        return ret;
    }

    public int getQtd()
    {
        int qtd = 0;
        No atual = this.primeiro;
        while(atual != null)
        {
            qtd++;
            atual = atual.getProx();
        }
        return qtd;
    }

    public int hashCode()
    {
        int ret = 345;
        No atual = this.primeiro;
        while(atual != null) {
            ret = ret * 7 + atual.hashCode();
            atual = atual.getProx();
        }

        if (ret < 0)
            return ret;

        return ret;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(this.getClass() != obj.getClass())
            return  false;

        ListaSimplesDesordenada modelo = (ListaSimplesDesordenada) obj;

        No atualDoThis = this.primeiro;
        No atualDoModelo = modelo.primeiro;

        while (atualDoThis != null && atualDoModelo != null)
        {
            if(!atualDoThis.getInfo().equals(atualDoModelo.getInfo()))
                return false;
            atualDoThis = atualDoThis.getProx();
            atualDoModelo = atualDoModelo.getProx();
        }

        if(atualDoThis != null || atualDoModelo != null)
            return false;

        return true;
    }

    public String toString()
    {
        String ret = "[ ";
        No atual = this.primeiro;
        while(atual != null)
        {
            ret += atual.getInfo() + " ";
            atual = atual.getProx();
        }
        ret += "]";
        return ret;
    }

    public ListaSimplesDesordenada(ListaSimplesDesordenada modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo nulo");
        if(modelo.primeiro == null)
            return;
        this.primeiro = new No((X)modelo.primeiro.getInfo());

        No atualDoThis = this.primeiro;
        No atualDoModelo = modelo.primeiro.getProx();

        while(atualDoModelo != null)
        {
            atualDoThis.setProx(new No(atualDoModelo.getInfo()));
            atualDoThis = atualDoThis.getProx();
            atualDoModelo = atualDoModelo.getProx();
        }
        this.ultimo = atualDoThis;
    }


}
