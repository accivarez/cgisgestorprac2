package es.us.isa.cgis.proyect.shared.demographic;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;


@Entity
public class Capability extends Locatable implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5672561629040757023L;
	private List<String> credentials;
	@ManyToOne
	private Role owner;
	
	protected Capability() {
    }
	
	public Capability(String uid, String archetypeNodeId, String name,
			String archetypeDetails,List<String> credentials, Role owner) 
	{
		super(uid, archetypeNodeId, name, archetypeDetails);
		if (credentials == null) {
            throw new IllegalArgumentException("null credentials");
        }
        this.credentials = credentials;
        this.owner = owner;
    }

    
    public List<String> getCredentials() {
        return credentials;
    }

    protected void setCredentials(List<String> credentials) {
        this.credentials = credentials;
    }

    
    @Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof Capability )) return false;
        if (!super.equals(o)) return false;

        final Capability capability = (Capability) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(credentials, capability.credentials)
            .append(owner, capability.credentials)
            .isEquals();
    }

    
    @Override
	public int hashCode() {
        return new HashCodeBuilder(13, 29)
            .appendSuper(super.hashCode())
            .append(credentials)
            .append(owner)
            .toHashCode();
    }
	
}
