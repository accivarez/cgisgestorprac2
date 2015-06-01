package es.usa.isa.cgis.proyect.client;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.us.isa.cgis.proyect.shared.demographic.Person;


@RemoteServiceRelativePath("patient")
public interface PatientService extends RemoteService {

	List<Person> getPatients();
	void updatePatients(Person p);
	Person getPatient(String key);
	void addPatient(Person p) throws IllegalArgumentException;
	void deletePatient(String key);
}
