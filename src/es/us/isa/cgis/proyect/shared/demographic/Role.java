package es.us.isa.cgis.proyect.shared.demographic;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class Role implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6064589196692513081L;
	@Id
	private long Id;
	private String uid;
	//private String archetypeNodeId;
	private String name;
	//private String archetypeDetails;
	private List<Capability> capabilities;
	
    private List<PartyRol> performers;
    
	public Role(String uid, String name,
			//reverseRelationship sera el conjunto de instancias de otras clases que hacen referencia a esta, mientras que relationship es el conjunto de Party a los que se hace referencia
			List<Capability> capabilities,
			List<PartyRol> performer)
	{
//		super(uid, archetypeNodeId, name, archetypeDetails, identities,
//				contacts, relationships, reverseRelationships, details);

		if (capabilities != null && capabilities.size() == 0) {
			throw new IllegalArgumentException("Empty capabilities");
		}
		if (performer == null) {
			throw new IllegalArgumentException("Null performer");
		}
		this.uid = uid;
		this.name = name;
		this.capabilities = capabilities;
		this.performers = performer;
	}
	
	public List<Capability> getCapabilities() {
        return capabilities;
    }

    protected void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public List<PartyRol> getPerformer() {
        return performers;
    }

    protected void setPerformer(List<PartyRol>  performer) {
        this.performers = performer;
    }

    
    @Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof Role )) return false;
        if (!super.equals(o)) return false;

        final Role role = (Role) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(capabilities, role.capabilities)
                .append(performers, role.performers)
                .isEquals();
    }

    @Override
	public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .appendSuper(super.hashCode())
                .append(capabilities)
                .append(performers)
                .toHashCode();
    }

	public String getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
	


