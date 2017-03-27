package br.edu.ifsul.controle;

import br.edu.ifsul.dao.GrupoDAO;
import br.edu.ifsul.modelo.Grupo;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "controleGrupo")
@SessionScoped
public class ControleGrupo implements Serializable {

    @EJB
    private GrupoDAO<Grupo> dao;
    private Grupo objeto;
    private Boolean editando;

    public ControleGrupo() {
        editando = false;
    }

    public String listar() {
        editando = false;
        return "/privado/grupo/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        objeto = new Grupo();

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
    }

    public Grupo getObjeto() {
        return objeto;
    }

    public void setObjeto(Grupo objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public GrupoDAO getDao() {
        return dao;
    }

    public void setDao(GrupoDAO dao) {
        this.dao = dao;
    }
}
