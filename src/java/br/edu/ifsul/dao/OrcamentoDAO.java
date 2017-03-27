package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Orcamento;
import br.edu.ifsul.modelo.OrcamentoItem;
import java.io.Serializable;
import javax.ejb.Stateful;



@Stateful
public class OrcamentoDAO<T> extends DAOGenerico<Orcamento> implements Serializable {

     
    public OrcamentoDAO() {
        super();
        super.setClassePersistente(Orcamento.class);
        super.setOrdem("nome");        
    }
 
   @Override
    public Orcamento getObjectById(Integer id) throws Exception {
        Orcamento obj = (Orcamento) super.getEm().find(super.getClassePersistente(), id);
        obj.getItens().size();
        
     for (OrcamentoItem i : obj.getItens()){
            i.getServico().getItens().size();
        }     
          
        return obj;
 
    }       
    
}
