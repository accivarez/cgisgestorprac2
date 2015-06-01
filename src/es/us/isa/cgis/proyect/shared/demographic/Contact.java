package es.us.isa.cgis.proyect.shared.demographic;

import java.util.List;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class Contact extends Locatable {
	protected Contact() {
	}

	public Contact(String ID, String archetypeNodeId, String name,
			String archetypeDetails, List<Address> addresses) {
		super(ID, archetypeNodeId, name, archetypeDetails);
		if (addresses == null || addresses.size() == 0) {
			throw new IllegalArgumentException("null or empty addresses");
		}
		this.addresses = addresses;
	}

	public String purpose() {
		return this.getName();
	}

	public String pathOfItem(Locatable item) {
		// todo: to be implemented
		return null;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	protected void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((addresses == null) ? 0 : addresses.hashCode());
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
		Contact other = (Contact) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		return true;
	}

	// @Override
	// public String pathOfItem(Pathable arg0) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public List<Object> itemsAtPath(String arg0) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean pathExists(String arg0) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean pathUnique(String arg0) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	private List<Address> addresses;
}
