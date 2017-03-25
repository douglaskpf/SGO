package br.edu.ifsul.controle;

import br.edu.ifsul.dao.InsumoDAO;
import br.edu.ifsul.dao.GrupoDAO;
import br.edu.ifsul.modelo.Insumo;
import br.edu.ifsul.modelo.Grupo;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.ServicoInsumo;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "controleInsumo")
@SessionScoped
public class ControleInsumo implements Serializable {

    @EJB
    private InsumoDAO<Insumo> dao;
    private Insumo objeto;
    private Boolean editando;
 
     
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
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.geMensagemErro(e));
        }

    }

    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");

        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover objeto: " + Util.geMensagemErro(e));
        }
    }
  
    
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
            Util.mensagemErro("Erro ao persistir: " + Util.geMensagemErro(e));
        }
        
         
        ////ver função para atualizar os valores dos insumos nos serviços que possuem esse insumo.
       
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

  
   
}
