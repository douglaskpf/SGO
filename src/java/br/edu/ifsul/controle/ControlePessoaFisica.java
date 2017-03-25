package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "controlePessoaFisica")
@SessionScoped
public class ControlePessoaFisica implements Serializable {

    @EJB
    private PessoaFisicaDAO<PessoaFisica> dao;
    private PessoaFisica objeto;
    private Boolean editando;
    
    @EJB
    private CidadeDAO<Cidade> daoCidade;
    
   
    public ControlePessoaFisica() {
        editando = false;
       
    }
    
    
    public String listar() {
        editando = false;
        return "/privado/pessoafisica/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        objeto = new PessoaFisica();

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
            

    public PessoaFisica getObjeto() {
        return objeto;
    }

    public void setObjeto(PessoaFisica objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public PessoaFisicaDAO getDao() {
        return dao;
    }

    public void setDao(PessoaFisicaDAO dao) {
        this.dao = dao;
    }

    public CidadeDAO<Cidade> getDaoCidade() {
        return daoCidade;
    }

    public void setDaoCidade(CidadeDAO<Cidade> daoCidade) {
        this.daoCidade = daoCidade;
    }

     
}
