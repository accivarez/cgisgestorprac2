package es.usa.isa.cgis.proyect.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import es.us.isa.cgis.proyect.server.entities.UserEntity;


public interface LoginServiceAsync {

	void changePassword(String name, String newPassword,
			AsyncCallback<Boolean> callback);

	void loginFromSessionServer(AsyncCallback<UserEntity> callback);

	void loginServer(String name, String password, AsyncCallback<UserEntity> callback);

	void logout(AsyncCallback<Void> callback);

}
