package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.ServicoInsumo;
import java.io.Serializable;
import javax.ejb.Stateful;


@Stateful
public class ServicoDAO<T> extends DAOGenerico<Servico> implements Serializable {
     
    public ServicoDAO() {
        super();
        super.setClassePersistente(Servico.class);
        super.setOrdem("nome");        
    }
     
    @Override
    public Servico getObjectById(Integer id) throws Exception {
        Servico obj = (Servico) super.getEm().find(super.getClassePersistente(), id);
        obj.getItens().size();
        
       for (ServicoInsumo s : obj.getItens()){
            s.getServico().getItens().size();
        }   
       return obj;
    }       
   
}
