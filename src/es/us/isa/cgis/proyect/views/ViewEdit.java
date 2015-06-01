package es.us.isa.cgis.proyect.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import es.us.isa.cgis.proyect.shared.demographic.Address;
import es.us.isa.cgis.proyect.shared.demographic.Contact;
import es.us.isa.cgis.proyect.shared.demographic.Person;
import es.usa.isa.cgis.proyect.client.GGIS_Proyect_Paciente;
import es.usa.isa.cgis.proyect.client.PatientService;
import es.usa.isa.cgis.proyect.client.PatientServiceAsync;


public class ViewEdit extends Composite{
 
	private final PatientServiceAsync patientService = GWT.create(PatientService.class);
	final TextBox id = new TextBox();
	final TextBox name = new TextBox();
	final TextBox contacts = new TextBox();
	final TextBox details = new TextBox();
	final Label errorLabel = new Label();
	private Person patientEntity=new Person();
	
	Set<Contact> s;
	
	public ViewEdit(Map<String, String> params) {
		
		// Panel principal e inicializacion de la vista 
		HorizontalPanel mainPanel = new HorizontalPanel();
		initWidget(mainPanel);
		
		final FlexTable formPanel = new FlexTable();
		Button saveButton = new Button("Save");
		
		
		if (params.containsKey("patientKey")){ // Estamos editando un paciente
			//TODO: como enviar la info??
			final String patientKey = params.get("patientKey");
			patientService.getPatient(patientKey, new AsyncCallback<Person>() {
	    		  @Override
				public void onSuccess(Person patient){
	    			  patientEntity = patient;
	    			  onGet(patient);
	    		  }
	    		  @Override
				public void onFailure(Throwable e){
	    			  Window.alert("Error retrieving patient: " + e.getMessage());
	    		  }
	    		  
	    	  });
			
			saveButton.addClickHandler(new ClickHandler() {
			      @Override
				public void onClick(ClickEvent event) {
			    	  errorLabel.setText("");
			          String textToServer = details.getText();
//			          if (!NUHSAVerifier.esNUHSAValido(textToServer)) {
//			            errorLabel.setText("El NUSHA no es valido");
//			            return;
//			          }
			    	  
			    	  Address a = new Address(contacts.getValue(), null, null, null, null);
			    	  List<Address> l = new ArrayList<Address>();
			    	  l.add(a);
			    	  Contact c = new Contact(null, null, null, null, l);
			    	  s = new HashSet<Contact>();
			    	  s.add(c);
			    	  patientEntity.setUid(id.getValue());
			    	  patientEntity.setName(name.getValue());
			    	  patientEntity.setContacts(s);
			    	  patientEntity.setDetails((details.getValue()));
			    	  patientService.updatePatients(patientEntity, new AsyncCallback<Void>() {
			    		  @Override
						public void onSuccess(Void v){
			    			  onSave();
			    		  }
			    		  @Override
						public void onFailure(Throwable e){
			    			  Window.alert("Error updating patient: " + e.getMessage());
			    		  }
			    		  
			    	  });
			      }
			    });	
		}else{	// Estamos creando un nuevo paciente
			saveButton.addClickHandler(new ClickHandler() {
			      @Override
				public void onClick(ClickEvent event) {
			    	  errorLabel.setText("");
//			          if (!NUHSAVerifier.esNUHSAValido(nuhsa.getText())) {
//			            errorLabel.setText("NUHSA Not Valid");
//			            return;
//			          }
//			          else{			        	  
				    	  Person patient = new Person(id.getValue(), null, name.getValue(), null, null, s, null, null, details.getValue(), null, null);
				    	  patientService.addPatient(patient, new AsyncCallback<Void>() {
				    		  @Override
							public void onSuccess(Void v){
				    			  onSave();
				    		  }
				    		  @Override
							public void onFailure(Throwable e){
				    			  Window.alert("Error adding patient: " + e.getMessage());
				    		  }
				    		  
				    	  });
//			          }
			      }
			    });
		}
		
		formPanel.setStylePrimaryName("updateTable");
		formPanel.setWidget(0,0, new Label("Name"));
		formPanel.setWidget(0,1, name);
		formPanel.setWidget(1,0, new Label("Contact"));
		formPanel.setWidget(1,1, contacts);
		formPanel.setWidget(2,0, new Label("Details"));
		formPanel.setWidget(2,1, details);
		mainPanel.add(formPanel);
		mainPanel.add(saveButton);
		errorLabel.setStylePrimaryName("serverResponseLabelError");
		mainPanel.add(errorLabel);
						
	}
	
	public void onGet(Person p){
		name.setValue(p.getName());
		contacts.setValue(p.getContacts().toString());
		details.setValue(p.getDetails());
	}
	
	public void onSave(){
		GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.LIST);
	}
	
}
