
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.PessoaFisica;
import java.io.Serializable;
import javax.ejb.Stateful;

@Stateful
public class PessoaFisicaDAO<T> extends DAOGenerico<PessoaFisica>implements Serializable {

    public PessoaFisicaDAO(){
        super();
        super.setClassePersistente(PessoaFisica.class);
        super.setOrdem("nome");// ordem padrão
    }

}
