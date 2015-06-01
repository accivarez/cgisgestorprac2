package es.us.isa.cgis.proyect.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import es.us.isa.cgis.proyect.shared.demographic.Person;
import es.usa.isa.cgis.proyect.client.GGIS_Proyect_Paciente;
import es.usa.isa.cgis.proyect.client.LoginService;
import es.usa.isa.cgis.proyect.client.LoginServiceAsync;
import es.usa.isa.cgis.proyect.client.PatientService;
import es.usa.isa.cgis.proyect.client.PatientServiceAsync;


public class ViewList extends Composite {
	
	
	private final PatientServiceAsync patientService = GWT.create(PatientService.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	
	private final FlexTable agendaPanel;
	private final HorizontalPanel mainPanel;
	
	public ViewList() {
		
		// Panel principal e inicializacion de la vista 
		mainPanel = new HorizontalPanel();
		initWidget(mainPanel);
		agendaPanel = new FlexTable();
		
		//Panel para la agenda
		agendaPanel.setStylePrimaryName("patientTable");
		agendaPanel.getRowFormatter().setStylePrimaryName(0,"firstRow");
		int columna = 0;
		agendaPanel.setWidget(0,columna++, new Label("ID"));
		agendaPanel.setWidget(0,columna++, new Label("Name"));
		agendaPanel.setWidget(0,columna++, new Label("Contact"));	
		agendaPanel.setWidget(0,columna++, new Label("Details"));
		agendaPanel.setWidget(0,columna++, new Label("Delete"));
		agendaPanel.setWidget(0,columna++, new Label("Update"));

		
		//Consultamos todos los pacientes
		patientService.getPatients(new AsyncCallback<List<Person>>(){
			@Override
			public void onSuccess(List<Person> a){
				//Rellenamos la tabla con los pacientes
				showPatients(a);
			}

			@Override
			public void onFailure(Throwable e) {
				Window.alert("Error loading patients: " + e.getMessage());
				
			}
		});			
			
		Button addButton = new Button("New");
		addButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {    	  
			    	// Activar la vista de creacion de pacientes
		    	  GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.CREATE);
		      }
		    });
		Button refreshButton = new Button("Refresh");
		refreshButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {    	  
			    	// Activar la vista de creacion de pacientes
		    	  GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.LIST);
		      }
		    });
		Button logoutButton = new Button("Logout");
		logoutButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {    	  
			    	// Hacer logout
		    	  loginService.logout(new AsyncCallback<Void>() {
		    		  @Override
					public void onSuccess(Void v){
		  				GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.INIT);
		  			}

		  			@Override
		  			public void onFailure(Throwable e) {
		  				Window.alert("Error logging out: " + e.getMessage());
		  				
		  			}
		    		  
				});
		      }
		    });
		mainPanel.add(agendaPanel);
		mainPanel.add(addButton);
		mainPanel.add(refreshButton);
		mainPanel.add(logoutButton);
	}
	
	
	/** METODO QUE INSERTA LOS DATOS DE LOS PACIENTES OBTENIDOS DE SERVIDOR Y LOS INSERTA EN LA TABLA (PANEL FLEXTABLE) */
	public final void showPatients(List<Person> patientsParam){
	 
		int i = 0;
		for (Person patient: patientsParam){
			final Person patientSearch = patient;
			int columna =0;
			agendaPanel.setWidget(i+1, columna++, new Label(patient.getUid()));
			agendaPanel.setWidget(i+1, columna++, new Label(patient.getName())); 
			agendaPanel.setWidget(i+1, columna++, new Label(patient.getContacts().toString())); //tlf
			agendaPanel.setWidget(i+1, columna++, new Label(patient.getDetails())); //nuhsa
 
			Button deleteButton = new Button("Delete");
			deleteButton.addClickHandler(new ClickHandler() {
			      @Override
				public void onClick(ClickEvent event) {
			        patientService.deletePatient(patientSearch.getUid().toString(), new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void a){
							GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.LIST);
						}

						@Override
						public void onFailure(Throwable e) {
							Window.alert("Error deleting patient: " + e.getMessage());
						}
			  		  }); 
			      }
			    });		
			agendaPanel.setWidget(i+1, columna++, deleteButton);
			
			
			Button updateButton = new Button("Update");
			updateButton.addClickHandler(new ClickHandler() {
			      @Override
				public void onClick(ClickEvent event) {
			    	//Activar la vista de edicion de paciente
			        Map<String,String> map = new HashMap<String,String>();
			        map.put("patientKey", patientSearch.getUid().toString());
			        GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.UPDATE,map);
			      }
			    });		
			agendaPanel.setWidget(i+1, columna++, updateButton);	
			i++;		    
		}

	}
}
