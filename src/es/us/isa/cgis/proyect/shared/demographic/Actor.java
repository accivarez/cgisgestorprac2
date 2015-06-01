package es.us.isa.cgis.proyect.shared.demographic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class Actor extends Party implements Serializable{
	

	private static final long serialVersionUID = -7279941247015873488L;
	private Set<Role> roles;
	private Set<String> languages;
	

	protected Actor() {
    }
	
	protected Actor(String uid, String archetypeNodeId,
			String name, String archetypeDetails,
			Set<PartyIdentity> identities, Set<Contact> contacts,
			Set<PartyRelationship> relationships, Set<Locatable> reverseRelationships,
			String details, Set<Role> roles, Set<String> languages) {
		
		super(uid, archetypeNodeId, name, archetypeDetails, identities, contacts, relationships, reverseRelationships, details);
		
        if (roles != null && roles.isEmpty()) {
            throw new IllegalArgumentException("empty roles");
        }
        if (languages != null && languages.isEmpty()) {
            throw new IllegalArgumentException("empty languages");
        }

        this.roles = roles;
        this.languages = languages;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	protected void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public boolean addRole(Role role) {
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		return roles.add(role);
	}


	public boolean removeRole(Role role) {
		if (roles == null) {
			return false;
		}
		return roles.remove(role);
	}


	public Set<String> getLanguages() {
		return languages;
	}

	protected void setLanguages(Set<String> languages) {
		this.languages = languages;
	}
	
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof Actor )) return false;
        if (!super.equals(o)) return false;

        final Actor actor = (Actor) o;
        return new EqualsBuilder()
                .append(roles, actor.roles)
                .append(languages, actor.languages)
                .isEquals();
    }

    
    @Override
	public int hashCode() {
        return new HashCodeBuilder(11, 29)
                .appendSuper(super.hashCode())
                .append(roles)
                .append(languages)
                .toHashCode();
    }
	
	
	


}
