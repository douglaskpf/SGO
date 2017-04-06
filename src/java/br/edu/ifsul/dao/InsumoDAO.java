
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Insumo;
import java.io.Serializable;
import javax.ejb.Stateful;

@Stateful
public class InsumoDAO<T> extends DAOGenerico<Insumo>implements Serializable {

    public InsumoDAO(){
        super();
        super.setClassePersistente(Insumo.class);
        super.setOrdem("nome");// ordem padrão
    }

    
    
    //fazer a função aqui
    
    
}
