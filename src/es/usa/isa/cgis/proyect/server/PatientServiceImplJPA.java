package es.usa.isa.cgis.proyect.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.us.isa.cgis.proyect.server.entities.EMF;
import es.us.isa.cgis.proyect.shared.demographic.Person;
import es.usa.isa.cgis.proyect.client.PatientService;


public class PatientServiceImplJPA extends RemoteServiceServlet implements
		PatientService {

	private static final long serialVersionUID = -6759193213038377169L;

	@Override
	public List<Person> getPatients() {
		EntityManager em = getEm();
		List<Person> listaPacientes = new ArrayList<>();
		try {

			TypedQuery<Person> q = em.createQuery(
					"SELECT * FROM Person p", Person.class);

			listaPacientes.addAll(q.getResultList());

		} finally {
			em.close();
		}

		return listaPacientes;
	}

	private EntityManager getEm() {
		return EMF.get().createEntityManager();

	}

	@Override
	public void updatePatients(Person updatePatient) {
		EntityManager em = getEm();
		//Patient patient = em.find(Patient.class,updatePatient.getKey());
		try {
			em.getTransaction().begin();
			em.merge(updatePatient);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public Person getPatient(String key) {
		EntityManager em = getEm();
		Person patient = em.find(Person.class,
				Long.parseLong(key));
		return patient;
	}

	@Override
	public void addPatient(Person p) {
//		if (!NUHSAVerifier.esNUHSAValido(p.getNUHSA())) {
//			IllegalArgumentException exception = new IllegalArgumentException(
//					"NUSHA not valid");
//			throw exception;
//		} else {
			EntityManager em = getEm();
			Person pe = new Person(p.getUid(), p.getArchetypeNodeId(), p.getName(), p.getArchetypeDetails(), p.getIdentities(), p.getContacts(), p.getRelationships(), p.getReverseRelationships(), p.getDetails(), p.getRoles(), p.getLanguages());
			try {
				em.getTransaction().begin();
				em.persist(pe);
				em.getTransaction().commit();
			} finally {
				em.close();
			}
//		}
	}

	@Override
	public void deletePatient(String key) {
		EntityManager em = getEm();
		Person patient = em.find(Person.class,
				Long.parseLong(key));

		try {
			em.getTransaction().begin();
			em.remove(patient);
			em.getTransaction().commit(); // Puede tardar tiempo en hacerlo
											// realmente.
		} finally {
			em.close();
		}
	}
}
