package es.us.isa.cgis.proyect.shared.demographic;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class PartyIdentity extends Locatable {

	private String details;
	
	protected PartyIdentity() {
	}

	public PartyIdentity(String ID, String archetypeNodeId,
			String name,
			String archetypeDetails,
			String details) {
		super(ID, archetypeNodeId, name, archetypeDetails);
		if (details == null) {
			throw new IllegalArgumentException("details null");
		}
		this.details = details;
	}


	@Override
	public String toString() {
		// todo: fix it
		return "";
	}

	public String getDetails() {
		return details;
	}

	protected void setDetails(String details) {
		this.details = details;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartyIdentity other = (PartyIdentity) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		return true;
	}



	
}
