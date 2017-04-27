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

    public void atualizarInsumo(Insumo in) throws Exception {
 
    
        Double aux = null; //váriável para pegar o novo valor do serviço

        //pegando todos os ServicoInsumo que possuem o Insumo
        List<ServicoInsumo> lista;
        lista = super.getEm().createQuery("from ServicoInsumo where insumo.id = '" + in.getId() + "'").getResultList();

        //Atualizando o valor total do ServicoInsumo
        for (ServicoInsumo si : lista) {
            si.setValorUnitario(in.getPreco());
            si.setValorTotal(si.getValorUnitario() * si.getQuantidade());
            super.getEm().merge(si);
        }

        //Atualizando o valorServico do Servico
        for (ServicoInsumo si : lista) {
            aux = 0.0;
            Servico s = si.getServico();
            s.setValorServico(0.0);

            for (ServicoInsumo si2 : s.getItens()) {
                aux += si2.getValorTotal();
            }
            s.setValorServico(aux);
            super.getEm().merge(s);
        }
    }
}
