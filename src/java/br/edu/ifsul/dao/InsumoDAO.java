package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Insumo;
import br.edu.ifsul.modelo.Servico;
import br.edu.ifsul.modelo.ServicoInsumo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;

@Stateful
public class InsumoDAO<T> extends DAOGenerico<Insumo> implements Serializable {

    public InsumoDAO() {
        super();
        super.setClassePersistente(Insumo.class);
        super.setOrdem("nome");// ordem padrão
    }

    /*
    public void atualizarInsumo(Integer id) throws Exception {

        Insumo in = null;
                  //pegando todos os ServicoInsumo que possuem o Insumo
        List<ServicoInsumo> lista;
        lista = super.getEm().createQuery("from ServicoInsumo where insumo.id = '" + id + "'").getResultList();

        //Atualizando o valor total do ServicoInsumo
        for (ServicoInsumo si : lista) {
            si.valorUnitario = in.preco;
            si.valorTotal = si.valorUnitario * si.quantidade;
            em.merge(si);
        }
        //Atualizando o valorServico do Servico
        for (ServicoInsumo si : lista) {
            Servico s = si.servico;
            s.valorServico = 0.0;

            for (ServicoInsumo si2 : s.itens) {
                s.valorServico += si2.valorTotal;
            }
            em.merge(s);
        }
    }  */
}
