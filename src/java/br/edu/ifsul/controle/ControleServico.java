package br.edu.ifsul.controle;

import br.edu.ifsul.dao.InsumoDAO;
import br.edu.ifsul.dao.ServicoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Insumo;
import br.edu.ifsul.modelo.ServicoInsumo;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named(value = "controleServico")
@SessionScoped
public class ControleServico implements Serializable {

    @EJB
    private ServicoDAO<Servico> dao;
    private Servico objeto;
    private Boolean editando;
    private Boolean novoItem; //controle de adição de itens
    
     
    @EJB
    private UsuarioDAO<Usuario> daoUsuario;
    
    
    @EJB
    private InsumoDAO<Insumo> daoInsumo;
    private Boolean editandoServicoInsumo;
    private ServicoInsumo item;

    public ControleServico() {
        editando = false;
        editandoServicoInsumo = false;
    }

    public String listar() {
        editando = false;
        return "/privado/servico/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoServicoInsumo = false;
        objeto = new Servico();
    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoServicoInsumo = false;
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
            editandoServicoInsumo = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + Util.geMensagemErro(e));
        }
    }

    public void novoServicoInsumo() {
        item = new ServicoInsumo();
        editandoServicoInsumo = true;
        novoItem = true;
    }

    public void salvarServicoInsumo() {
        if (item.getId() == null && novoItem == true) {
            objeto.adicionarItem(item);
        }        
        
        objeto.setValorServico(0.00);
        Double total = 0.0;  
        for (ServicoInsumo si : objeto.getItens()) {
            total += si.getValorTotal();
        }
       
        objeto.setValorServico(total);
          
        editandoServicoInsumo = false;
        Util.mensagemInformacao("Item persistido com sucesso!");
        
            
    }

    public void alterarServicoInsumo(int index) {
        item = objeto.getItens().get(index);
        editandoServicoInsumo = true;
        novoItem = false;
    }

    public void excluirServicoInsumo(int index) {
        objeto.removerItem(index);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
        
     public void atualizaValorUnitarioItem() {
        if (item != null) {
            if (item.getInsumo() != null) {
                item.setValorUnitario(item.getInsumo().getPreco());
            }
        }
    }

    public void calculaValorTotalItem() {
        if (item.getValorUnitario() != null && item.getQuantidade() != null) {
            item.setValorTotal(item.getValorUnitario() * item.getQuantidade());
        }
        
    } 
    
        
    /*    private void atualizaValorTotal() {
        objeto.setValorServico(0.00);
        Double total = 0.0;
        for (ServicoInsumo si : objeto.getItens()) {
            total += si.getValorTotal();
        }
        objeto.setValorServico(total);
    }*/
         

    public Servico getObjeto() {
        return objeto;
    }

    public void setObjeto(Servico objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public ServicoDAO<Servico> getDao() {
        return dao;
    }

    public void setDao(ServicoDAO<Servico> dao) {
        this.dao = dao;
    }

   
    public Boolean getEditandoServicoInsumo() {
        return editandoServicoInsumo;
    }

    public void setEditandoServicoInsumo(Boolean editandoServicoInsumo) {
        this.editandoServicoInsumo = editandoServicoInsumo;
    }

    public ServicoInsumo getServicoInsumo() {
        return item;
    }

    public void setServicoInsumo(ServicoInsumo item) {
        this.item = item;
    }

    public InsumoDAO<Insumo> getDaoInsumo() {
        return daoInsumo;
    }

    public void setDaoInsumo(InsumoDAO<Insumo> daoInsumo) {
        this.daoInsumo = daoInsumo;
    }

    public ServicoInsumo getItem() {
        return item;
    }

    public void setItem(ServicoInsumo item) {
        this.item = item;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }
}
