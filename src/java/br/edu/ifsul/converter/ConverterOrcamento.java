package br.edu.ifsul.converter;

import br.edu.ifsul.modelo.Orcamento;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@FacesConverter(value = "converterOrcamento")
public class ConverterOrcamento implements Serializable, Converter {

    @PersistenceContext(unitName = "SGO-WEBPU")
    private EntityManager em;

    // converte da tela para o objeto
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals("Selecione um registro")) {
            return null;
        }
        return em.find(Orcamento.class, Integer.parseInt(string));
    }
    // converte do objeto para a tela
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Orcamento obj = (Orcamento) o;
        return obj.getId().toString();
    }

}
