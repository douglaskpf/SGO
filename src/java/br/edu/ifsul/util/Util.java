
package br.edu.ifsul.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class Util {
    
    
      public static String getMensagemErro(Exception e){
        while(e.getCause() != null){
            e = (Exception) e.getCause();
        }
        String retorno = e.getMessage();
        if (retorno.contains("foreign key")){
            retorno = "Registro não pode ser excluido por "
                    + "possuir referência em outros objetos...";            
        }
        return retorno;
    }
    
    public static void mensagemErro(String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

      
    public static void mensagemInformacao(String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
   
}	