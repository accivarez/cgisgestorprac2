package es.us.isa.cgis.proyect.shared.demographic;

import java.io.Serializable;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class Address extends Locatable implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -3347545613092226875L;



	protected Address() {
	}

	public Address(String ID, String archetypeNodeId, String name,
			String archetypeDetails, String details) {
		super(ID, archetypeNodeId, name, archetypeDetails);
		this.details = details;
	}

	public String pathOfItem(Locatable item) {
		// todo: to be implemented
		return null;
	}

	public String type() {
		return this.getName();
	}

	@Override
	public String toString() {
		// todo: fix it
		// return details.toString();
		return "";
	}

	public String getDetails() {
		return details;
	}

	protected void setDetails(String details) {
		this.details = details;
	}

	private String details;
}
