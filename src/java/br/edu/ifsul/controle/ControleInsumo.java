package br.edu.ifsul.controle;

import br.edu.ifsul.dao.InsumoDAO;
import br.edu.ifsul.dao.GrupoDAO;
import br.edu.ifsul.dao.ServicoDAO;
import br.edu.ifsul.modelo.Insumo;
import br.edu.ifsul.modelo.Grupo;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.ServicoInsumo;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named(value = "controleInsumo")
@SessionScoped
public class ControleInsumo implements Serializable {

    @EJB
    private InsumoDAO<Insumo> dao;
    private Insumo objeto;
    private Boolean editando;
    
      
    //testes
    @EJB
    private ServicoDAO<Servico> daoServico;
    private Servico objetoServico;
    private ServicoInsumo item;
     
      
    @EJB
    private GrupoDAO<Grupo> daoGrupo;
             
    
    public ControleInsumo() {
        editando = false;
    }

    public String listar() {
        editando = false;
        return "/privado/insumo/listar?faces-redirect=true";
    }

     public String listarUser() {
        editando = false;
        return "/privado/insumoUser/listar?faces-redirect=true";
    }
    
    public void novo() {
        editando = true;
        objeto = new Insumo();

    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;

        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }

    }

    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");

        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }  
  
   /*public void atualizarInsumo(Integer id){
   
           try{
              ServicoInsumo si = null;    
              Insumo in = null;
              Servico s = null;
                             
              //pegando todos os ServicoInsumo que possuem o Insumo
           List<ServicoInsumo> lista;
                lista = em.createQuery("from ServicoInsumo  where insumo.id = '" + id +"'").getResultList();
               
                
                //Atualizando o valor total do ServicoInsumo
               for(ServicoInsumo si : lista){
                   si.setValorUnitario(in.getPreco());
                   si.getValorTotal()= (si.getValorUnitario() * si.getQuantidade());
                   em.merge(si);
                   
               }    
               
               //Atualizando o valorServico do Servico
                    for (ServicoInsumo si : lista){
                       Servico s = si.getServico();
                        s.setValorServico(0.0);
                      
                        for (ServicoInsumo si2 : s.getItens()){
                            s.setValorServico(si2.setValorTotal());
                                          
                        
                        }
                        
                    }    
                    
         
                  
    } catch (Exception e) {
            Util.mensagemErro("Erro ao atualizar o valor do insumo: " + Util.getMensagemErro(e));
        }
 
       }
   
       */
       
    
    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
                            
            }
            Util.mensagemInformacao("Sucesso ao persistir objeto");
            editando = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + Util.getMensagemErro(e));
        }
                   
        //após atualizar o preço do "Insumo"//             
        //percorrer a classe "ServicoInsumo" e atualizar o insumo e o valor total do "ServicoInsumo"//
        //após atualizar a classe "servicoInsumo" mandar o "novo" total para o valor_servico em "Servico"//
            
        
      //  atualizarInsumo();
   
    }     
        
   public Insumo getObjeto() {
        return objeto;
    }

    public void setObjeto(Insumo objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public InsumoDAO getDao() {
        return dao;
    }

    public void setDao(InsumoDAO dao) {
        this.dao = dao;
    }

    public GrupoDAO<Grupo> getDaoGrupo() {
        return daoGrupo;
    }

    public void setDaoGrupo(GrupoDAO<Grupo> daoGrupo) {
        this.daoGrupo = daoGrupo;
    }

    public ServicoInsumo getItem() {
        return item;
    }

    public void setItem(ServicoInsumo item) {
        this.item = item;
    }

    public ServicoDAO<Servico> getDaoServico() {
        return daoServico;
    }

    public void setDaoServico(ServicoDAO<Servico> daoServico) {
        this.daoServico = daoServico;
    }

    public Servico getObjetoServico() {
        return objetoServico;
    }

    public void setObjetoServico(Servico objetoServico) {
        this.objetoServico = objetoServico;
    }
   
}
