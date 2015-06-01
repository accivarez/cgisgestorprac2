package es.us.isa.cgis.proyect.shared.demographic;

import java.util.Set;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;




public class Organisation extends Actor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2693453453031389897L;

	protected Organisation(){
		
	}
	
	public Organisation(String uid, String archetypeNodeId,
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
