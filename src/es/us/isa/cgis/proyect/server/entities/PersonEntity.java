package es.us.isa.cgis.proyect.server.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

import es.us.isa.cgis.proyect.shared.archetypes.Locatable;
import es.us.isa.cgis.proyect.shared.demographic.Contact;
import es.us.isa.cgis.proyect.shared.demographic.PartyIdentity;
import es.us.isa.cgis.proyect.shared.demographic.PartyRelationship;
import es.us.isa.cgis.proyect.shared.demographic.Person;
import es.us.isa.cgis.proyect.shared.demographic.Role;

@Entity
public class PersonEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6711709134122469125L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Key ID;
	
	private String clinicID;
	private List<Evaluation> evaluations;
	private List<Action> actions;
	private List<Observation> obvs;
	private List<Instruction> instructions;
	
	public PersonEntity() {
	}

	public PersonEntity(String uid, String archetypeNodeId, String name,
			String archetypeDetails, Set<PartyIdentity> identities,
			Set<Contact> contacts, Set<PartyRelationship> relationships,
			Set<Locatable> reverseRelationships, String details,
			Set<Role> roles, Set<String> languages) {
		
	}
	
}
