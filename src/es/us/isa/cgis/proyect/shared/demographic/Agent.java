package es.us.isa.cgis.proyect.shared.demographic;

import java.util.Set;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class Agent extends Actor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1428317440941632917L;

	protected Agent() {
    }

	public Agent(String uid, String archetypeNodeId,
			String name, String archetypeDetails,
			Set<PartyIdentity> identities, Set<Contact> contacts,
			Set<PartyRelationship> relationships, Set<Locatable> reverseRelationships,
			String details, Set<Role> roles, Set<String> languages) 
	{	
		 super(uid, archetypeNodeId, name, archetypeDetails,
	                identities, contacts, relationships, reverseRelationships,
	                details, roles, languages);	
	}

}
