package es.usa.isa.cgis.proyect.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.us.isa.cgis.proyect.server.entities.UserEntity;



@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService
{
    /**
     * Utility class for simplifying access to the instance of async service.
     */
 
    UserEntity loginServer(String name, String password);
 
    UserEntity loginFromSessionServer();
     
    boolean changePassword(String name, String newPassword);
 
    void logout();
}