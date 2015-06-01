package es.us.isa.cgis.proyect.shared.demographic;

import java.util.List;
import java.util.Set;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;


public abstract class Party extends Locatable {

	    protected Party() {
	    }

	    
	    protected Party(String ID, String archetypeNodeId, String name,
	                    String archetypeDetails, Set<PartyIdentity> identities,
	                    Set<Contact> contacts, Set<PartyRelationship> relationships,
	                    Set<Locatable> reverseRelationships,
	                    String details) {
	        super(ID, archetypeNodeId, name, archetypeDetails);

	        if (ID == null) {
	            throw new IllegalArgumentException("null uid");
	        }
	        if (identities == null || identities.isEmpty()) {
	            throw new IllegalArgumentException("null or empty identities");
	        }
	        if (contacts != null && contacts.isEmpty()) {
	            throw new IllegalArgumentException("empty contacts");
	        }
	        if (relationships != null) {
	            if (relationships.isEmpty()) {
	                throw new IllegalArgumentException("empty relationships");
	            }
	            boolean hasThis = false;
	            for (PartyRelationship relation : relationships) {
	                //if (relation.getSource().getId().equals(getUid())) {
	            	if (relation.equals(getUid())) {
	                    hasThis = true;
	                    break;
	                }
	            }
	            if (!hasThis) {
	                throw new IllegalArgumentException("invalid relationships");
	            }
	        }

	        //todo: Reverse_releationships_validity

	        if (!isArchetypeRoot()) {
	            throw new IllegalArgumentException("not archetype root");
	        }
	        this.identities = identities;
	        this.contacts = contacts;
	        this.relationships = relationships;
	        this.reverseRelationships = reverseRelationships;
	        this.details = details;
	    }

	    
	    public String type() {
	        return this.getName();
	    }

	    public String pathOfItem(Locatable item) {
	        //todo: to be implemented
	        return null;
	    }

	    public Set<PartyIdentity> getIdentities() {
	        return identities;
	    }

	    protected void setIdentities(Set<PartyIdentity> identities) {
	        this.identities = identities;
	    }

	    public Set<Contact> getContacts() {
	        return contacts;
	    }

	    public void setContacts(Set<Contact> contacts) {
	        this.contacts = contacts;
	    }

	    public Set<PartyRelationship> getRelationships() {
	        return relationships;
	    }

	    protected void setRelationships(Set<PartyRelationship> relationships) {
	        this.relationships = relationships;
	    }

	    public Set<Locatable> getReverseRelationships() {
	        return reverseRelationships;
	    }

	    protected void setReverseRelationships(
	            Set<Locatable> reverseRelationships) {
	        this.reverseRelationships = reverseRelationships;
	    }

	    public String getDetails() {
	        return details;
	    }

	    public void setDetails(String details) {
	        this.details = details;
	    }

	    

	    


	    
	    @Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result
					+ ((contacts == null) ? 0 : contacts.hashCode());
			result = prime * result
					+ ((details == null) ? 0 : details.hashCode());
			result = prime * result
					+ ((identities == null) ? 0 : identities.hashCode());
			result = prime * result
					+ ((refPartyRole == null) ? 0 : refPartyRole.hashCode());
			result = prime * result
					+ ((relationships == null) ? 0 : relationships.hashCode());
			result = prime
					* result
					+ ((reverseRelationships == null) ? 0
							: reverseRelationships.hashCode());
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
			Party other = (Party) obj;
			if (contacts == null) {
				if (other.contacts != null)
					return false;
			} else if (!contacts.equals(other.contacts))
				return false;
			if (details == null) {
				if (other.details != null)
					return false;
			} else if (!details.equals(other.details))
				return false;
			if (identities == null) {
				if (other.identities != null)
					return false;
			} else if (!identities.equals(other.identities))
				return false;
			if (refPartyRole == null) {
				if (other.refPartyRole != null)
					return false;
			} else if (!refPartyRole.equals(other.refPartyRole))
				return false;
			if (relationships == null) {
				if (other.relationships != null)
					return false;
			} else if (!relationships.equals(other.relationships))
				return false;
			if (reverseRelationships == null) {
				if (other.reverseRelationships != null)
					return false;
			} else if (!reverseRelationships.equals(other.reverseRelationships))
				return false;
			return true;
		}

		protected Set<PartyIdentity> identities;
	    private Set<Contact> contacts;
	    private Set<PartyRelationship> relationships;
	    private Set<Locatable> reverseRelationships;
	    private String details;
	    
	    private List<String> refPartyRole;
}
