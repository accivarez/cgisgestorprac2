package es.us.isa.cgis.proyect.shared.demographic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;


public class PartyRelationship extends Locatable {
	
	private String details;
    private Party source;
    private Party target;	
	
	public PartyRelationship(String ID, String archetypeNodeID, String name, String archetypeDetails, String details, Party source, Party target){
		super(ID, archetypeNodeID, name, archetypeDetails);

        if (ID == null) {
            throw new IllegalArgumentException("null uid");
        }


        if (source == null) {
            throw new IllegalArgumentException("null source");
        }
        if (target == null) {
            throw new IllegalArgumentException("null target");
        }
        this.details = details;
        this.source = source;
        this.target = target;
	}

    
    public String type() {
        return getName();
    }

    public String getDetails() {
        return details;
    }

    protected void setDetails(String details) {
        this.details = details;
    }
    

    public Party getSource() {
        return source;
    }

    protected void setSource(Party source) {
        this.source = source;
    }

    public Party getTarget() {
        return target;
    }

    protected void setTarget(Party target) {
        this.target = target;
    }

    
    @Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof PartyRelationship )) return false;
        if (!super.equals(o)) return false;

        final PartyRelationship relation = (PartyRelationship) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(details, relation.details)
                .append(source, relation.source)
                .append(target, relation.target)
                .isEquals();
    }

    
    @Override
	public int hashCode() {
        return new HashCodeBuilder(7, 13)
                .appendSuper(super.hashCode())
                .append(details)
                .append(source)
                .append(target)
                .toHashCode();
    }

   
	
	
}
