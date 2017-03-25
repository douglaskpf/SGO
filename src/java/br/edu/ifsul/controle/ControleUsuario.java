package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

@Named(value = "controleUsuario")
@SessionScoped
public class ControleUsuario implements Serializable {

    @EJB
    private UsuarioDAO<Usuario> dao;
    private Usuario objeto;
    private Boolean editando;
    
    @EJB
    private PessoaFisicaDAO<PessoaFisica> daoPessoaFisica;
    
    @EJB
    private CidadeDAO<Cidade> daoCidade;

    public ControleUsuario() {
        editando = false;

    }

    public String listar() {
        editando = false;
        return "/privado/usuario/listar?faces-redirect=true";
    }
    
     public String listarUser() {
        editando = false;
        return "/privado/usuarioUser/listar?faces-redirect=true";
    }
     
    public void novo() {
        editando = true;
        objeto = new Usuario();
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
            Util.mensagemErro("Erro a remover objeto: " + Util.geMensagemErro(e));
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

    public Usuario getObjeto() {
        return objeto;
    }

    public void setObjeto(Usuario objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public UsuarioDAO<Usuario> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<Usuario> dao) {
        this.dao = dao;
    }

    public PessoaFisicaDAO<PessoaFisica> getDaoPessoaFisica() {
        return daoPessoaFisica;
    }

    public void setDaoPessoaFisica(PessoaFisicaDAO<PessoaFisica> daoPessoaFisica) {
        this.daoPessoaFisica = daoPessoaFisica;
    }

    public CidadeDAO<Cidade> getDaoCidade() {
        return daoCidade;
    }

    public void setDaoCidade(CidadeDAO<Cidade> daoCidade) {
        this.daoCidade = daoCidade;
    }

}
