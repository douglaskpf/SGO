
package br.edu.ifsul.converter;

import br.edu.ifsul.modelo.Estado;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@FacesConverter(value = "converterEstado")
public class ConverterEstado implements Serializable, Converter {
   
    
    @PersistenceContext(unitName = "SGO-WEBPU")
    private EntityManager em;
    
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals("Selecione um registro")) {
            return null;
        }
        return em.find(Estado.class, Integer.parseInt(string));
    }

    // converte do objeto para a tela
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        }
        Estado obj = (Estado) o;
        return obj.getId().toString();
    }
}
