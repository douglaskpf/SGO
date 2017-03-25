package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ServicoDAO;
import br.edu.ifsul.dao.OrcamentoDAO;
import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.OrcamentoItem;
import br.edu.ifsul.modelo.Orcamento;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named(value = "controleOrcamento")
@SessionScoped
public class ControleOrcamento implements Serializable {

    @EJB
    private OrcamentoDAO<Orcamento> dao;
    private Orcamento objeto;
    private Boolean editando;
    private Boolean novoItem; //controle de adição de itens
    
    @EJB
    private UsuarioDAO<Usuario> daoUsuario;
    
    @EJB
    private PessoaFisicaDAO<PessoaFisica> daoPessoaFisica;
    
    @EJB
    private ServicoDAO<Servico> daoServico;
    private Boolean editandoOrcamentoItem;
    private OrcamentoItem item;
       
       
    public ControleOrcamento() {
        editando = false;
        editandoOrcamentoItem = false;
    }
    
    public void imprimeOrcamentoTeste(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioOrcamento2", 
                parametros, dao.getListaTodos());
    }
    
    public void imprimeOrcamento(Integer id){
        objeto = dao.localizar(id);
        List<Orcamento> listaOrcamento = new ArrayList<>();
        listaOrcamento.add(objeto);
        HashMap parametros = new HashMap();
      //  parametros.put("listaServicos", objeto.getItens());
        UtilRelatorios.imprimeRelatorio("relatorioOrcamento", parametros,
                listaOrcamento);
    }


    public String listar() {
        editando = false;
        return "/privado/orcamento/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoOrcamentoItem = false;
        objeto = new Orcamento();
        objeto.setData(Calendar.getInstance());
        
    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoOrcamentoItem = false;
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
            editandoOrcamentoItem = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + Util.geMensagemErro(e));
        }
    }

    public void novoOrcamentoItem() {
        item = new OrcamentoItem();
        editandoOrcamentoItem = true;
        novoItem = true;
    }

    public void salvarOrcamentoItem() {
       
        //criar atributo booleano para novo item == true e edição == false
                      
        if (item.getId() == null && novoItem == true) {
                objeto.adicionarItem(item);
        }
             
        objeto.setValorTotal(0.00);
        Double total = 0.0;    
        for (OrcamentoItem oi : objeto.getItens()) {
            total += oi.getValorTotal();
        }
       objeto.setValorTotal(total);
       editandoOrcamentoItem = false;
       Util.mensagemInformacao("Item persistido com sucesso!");
    }

    public void alterarOrcamentoItem(int index) {
        item = objeto.getItens().get(index);
        editandoOrcamentoItem = true;
        novoItem = false;
    }

    public void excluirOrcamentoItem(int index) {
        objeto.removerItem(index);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
    
    
    
     public void atualizaValorUnitarioItem() {
        if (item != null) {
            if (item.getServico() != null) {
                item.setValorUnitario(item.getServico().getValorServico());
            }
        }
    }

   

    public void calculaValorTotalItem() {
        if (item.getValorUnitario() != null && item.getQuantidade() != null) {
            item.setValorTotal(item.getValorUnitario() * item.getQuantidade());
        }
             
    } 
      
        
     /*private void atualizaValorTotal() {
        objeto.setValorTotal(0.00);
        Double total = 0.0;
        for (OrcamentoItem oi : objeto.getItens()) {
            total += oi.getValorTotal();
        }
        objeto.setValorTotal(total);
    }*/
         
         

    public Orcamento getObjeto() {
        return objeto;
    }

    public void setObjeto(Orcamento objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public OrcamentoDAO<Orcamento> getDao() {
        return dao;
    }

    public void setDao(OrcamentoDAO<Orcamento> dao) {
        this.dao = dao;
    }

   
    public Boolean getEditandoOrcamentoItem() {
        return editandoOrcamentoItem;
    }

    public void setEditandoOrcamentoItem(Boolean editandoOrcamentoItem) {
        this.editandoOrcamentoItem = editandoOrcamentoItem;
    }

    public OrcamentoItem getOrcamentoItem() {
        return item;
    }

    public void setOrcamentoItem(OrcamentoItem item) {
        this.item = item;
    }

    public ServicoDAO<Servico> getDaoServico() {
        return daoServico;
    }

    public void setDaoServico(ServicoDAO<Servico> daoServico) {
        this.daoServico = daoServico;
    }

    public OrcamentoItem getItem() {
        return item;
    }

    public void setItem(OrcamentoItem item) {
        this.item = item;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public PessoaFisicaDAO<PessoaFisica> getDaoPessoaFisica() {
        return daoPessoaFisica;
    }

    public void setDaoPessoaFisica(PessoaFisicaDAO<PessoaFisica> daoPessoaFisica) {
        this.daoPessoaFisica = daoPessoaFisica;
    }

   
}
