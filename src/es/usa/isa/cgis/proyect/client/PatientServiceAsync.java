package es.usa.isa.cgis.proyect.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import es.us.isa.cgis.proyect.shared.demographic.Person;

public interface PatientServiceAsync {

	void getPatients(AsyncCallback<List<Person>> callback);

	void updatePatients(Person p, AsyncCallback<Void> callback);

	void addPatient(Person p, AsyncCallback<Void> callback);

	void deletePatient(String key, AsyncCallback<Void> callback);

	void getPatient(String key, AsyncCallback<Person> callback);

}
