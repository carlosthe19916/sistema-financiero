package org.venturabank.managedbean.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.ventura.entity.Estadocivil;
import org.ventura.entity.Sexo;
import org.venturabank.managedbean.PersonaNaturalClienteMB;
import org.venturabank.managedbean.PersonaNaturalMB;

@ManagedBean
@ConversationScoped
public class mostrarDatosClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{personaNaturalMB}")
	private PersonaNaturalMB personaNaturalMB;
	
	@ManagedProperty(value = "#{personaNaturalClienteMB}")
	private PersonaNaturalClienteMB personaNaturalClienteMB;
	
	@Inject
    private Conversation conversation;
	
	public mostrarDatosClienteMB() {
		
	}
	
	
	public PersonaNaturalMB getPersonaNaturalMB() {
		return personaNaturalMB;
	}

	public void setPersonaNaturalMB(PersonaNaturalMB personaNaturalMB) {
		this.personaNaturalMB = personaNaturalMB;
	}
	
	public PersonaNaturalClienteMB getPersonaNaturalClienteMB() {
		return personaNaturalClienteMB;
	}

	public void setPersonaNaturalClienteMB(
			PersonaNaturalClienteMB personaNaturalClienteMB) {
		this.personaNaturalClienteMB = personaNaturalClienteMB;
	}

	public void beginConversation()
	   {
	      if (conversation.isTransient())
	      {
	          conversation.begin();
	      }
	   }
	 
	   public void endConversation()
	   {
	      if (!conversation.isTransient())
	      {
	          conversation.end();
	      }
	   }
	
	public void imprimirDatos(){
		this.personaNaturalMB.setPersonaNatural(this.personaNaturalClienteMB.getSelectedPersonaNaturalCliente().getPersonanatural());
		System.out.println("Llego  recuperar datos");
		System.out.println(personaNaturalClienteMB.getSelectedPersonaNaturalCliente().getDni());
		System.out.println(personaNaturalMB.getPersonaNatural().getDni());
		System.out.println(personaNaturalMB.getPersonaNatural().getNombreCompleto());
	}

}
