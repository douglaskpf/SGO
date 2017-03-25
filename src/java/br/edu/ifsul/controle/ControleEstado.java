package br.edu.ifsul.controle;

import br.edu.ifsul.dao.EstadoDAO;
import br.edu.ifsul.modelo.Estado;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "controleEstado")
@SessionScoped
public class ControleEstado implements Serializable {

    @EJB
    private EstadoDAO<Estado> dao;
    private Estado objeto;
    private Boolean editando;

    public ControleEstado() {
        editando = false;
    }

    public String listar() {
        editando = false;
        return "/privado/estado/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        objeto = new Estado();

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
    }

    public Estado getObjeto() {
        return objeto;
    }

    public void setObjeto(Estado objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public EstadoDAO getDao() {
        return dao;
    }

    public void setDao(EstadoDAO dao) {
        this.dao = dao;
    }
}
