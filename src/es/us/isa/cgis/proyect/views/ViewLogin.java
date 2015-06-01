package es.us.isa.cgis.proyect.views;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.us.isa.cgis.proyect.server.entities.UserEntity;
import es.usa.isa.cgis.proyect.client.GGIS_Proyect_Paciente;
import es.usa.isa.cgis.proyect.client.LoginService;
import es.usa.isa.cgis.proyect.client.LoginServiceAsync;
import es.usa.isa.cgis.proyect.shared.UserVerifier;



public class ViewLogin extends Composite {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	private final VerticalPanel mainPanel;

	final Label errorLabel = new Label();

	public ViewLogin() {

		// Panel principal e inicializacion de la vista
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);

		final TextBox txtfldUserName = new TextBox();
		txtfldUserName.setTitle("User Name");

		final PasswordTextBox txtfldPassword = new PasswordTextBox();
		txtfldPassword.setTitle("Password");

		final Button btnLogin = new Button("Login");
		btnLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!UserVerifier.esLoginValido(txtfldUserName.getValue())) {
					errorLabel.setText("Login no puede estar vacio");
					return;
				}
				if (!UserVerifier.esPasswordValido(txtfldPassword.getValue())) {
					errorLabel.setText("Password no puede estar vacio");
					return;
				}
				loginService.loginServer(txtfldUserName.getValue(), txtfldPassword.getValue(), new AsyncCallback<UserEntity>() {
					@Override
					public void onSuccess(UserEntity result) {
						if (result!=null) {
							String sessionID = result.getSessionId();
							final long DURATION = 1000 * 60 * 60 * 24 * 1;
							Date expires = new Date(System.currentTimeMillis() + DURATION);
							Cookies.setCookie("sid", sessionID, expires, null, "/", false);
							GGIS_Proyect_Paciente.go(GGIS_Proyect_Paciente.LIST);
						}
						else{
							Window.alert("Access Denied. Check your user-name and password.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Access Denied. Check your user-name and password.");
					}
				});
			}
		});
		
		mainPanel.add(txtfldUserName);
		mainPanel.add(txtfldPassword);
		errorLabel.setStylePrimaryName("serverResponseLabelError");
		mainPanel.add(errorLabel);
		mainPanel.add(btnLogin);
	}
}
