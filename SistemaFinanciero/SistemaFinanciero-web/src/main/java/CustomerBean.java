import java.io.Serializable;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;

@Named
@FlowScoped("customer")
public class CustomerBean implements Serializable {

	private String firstName;
	private String lastName;
	
	public CustomerBean() {

		System.out.println("CustomerBean has been created...");

	}

	public String uno(){
		System.out.println("entro11111");
		return "customerA";
	}
	
	public String dos(){
		System.out.println("entro222");
		return "customerB";
	}
	
	public String getName() {

		return this.getClass().getSimpleName();

	}

	public String getReturnValue() {

		return "/index";

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
