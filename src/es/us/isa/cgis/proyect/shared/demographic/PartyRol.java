package es.us.isa.cgis.proyect.shared.demographic;

public class PartyRol {

	String ID;
	Role rol;
	Party party;
	public PartyRol(String ID, Role rol, Party party) {
		this.ID = ID;
		this.rol = rol;
		this.party = party;
	}
	

	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public Role getRol() {
		return rol;
	}
	
	public void setRol(Role rol) {
		this.rol = rol;
	}
	
	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PartyRol)) {
			return false;
		}
		PartyRol other = (PartyRol) obj;
		if (ID == null) {
			if (other.ID != null) {
				return false;
			}
		} else if (!ID.equals(other.ID)) {
			return false;
		}
		if (rol == null) {
			if (other.rol != null) {
				return false;
			}
		} else if (!rol.equals(other.rol)) {
			return false;
		}
		return true;
	}
	

}
