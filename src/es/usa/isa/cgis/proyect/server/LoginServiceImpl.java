package es.usa.isa.cgis.proyect.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.us.isa.cgis.proyect.server.entities.EMF;
import es.us.isa.cgis.proyect.server.entities.UserEntity;
import es.usa.isa.cgis.proyect.client.LoginService;



public class LoginServiceImpl extends RemoteServiceServlet implements LoginService
{
	private static final long serialVersionUID = 4456105400553118785L;

	
	@Override
	public void init(){
		/*Crear algunos usuarios de manera manual*/
		UserEntity usuario = new UserEntity();
		usuario.setLogin("admin");
		usuario.setPassword("admin");
		EntityManager em = getEm();
		try {
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	private EntityManager getEm() {
		return EMF.get().createEntityManager();

	}

	@Override
	public UserEntity loginServer(String login, String password)
	{
		EntityManager em = getEm();
		List<UserEntity> listaUsuarios = new ArrayList<>();
		UserEntity userFound = null;
		try {

			TypedQuery<UserEntity> q = em.createQuery(
					"SELECT u FROM User u WHERE u.login = :login", UserEntity.class);
			q.setParameter("login",login);

			listaUsuarios = q.getResultList();
			if(!listaUsuarios.isEmpty()){
				userFound = listaUsuarios.get(0);
				if(userFound.getLogin().equals(login) && userFound.getPassword().equals(password)){
                    userFound.setSessionId(this.getThreadLocalRequest().getSession().getId());
					storeUserInSession(userFound);
				}
			}
		}
		finally{
			em.close();
		}

		return userFound;
	}

	@Override
	public UserEntity loginFromSessionServer()
	{
		return getUserAlreadyFromSession();
	}

	@Override
	public void logout()
	{
		deleteUserFromSession();
	}

	@Override
	public boolean changePassword(String name, String newPassword)
	{
		//TODO:change password logic
		return true;
	}

	private UserEntity getUserAlreadyFromSession()
	{
		UserEntity user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute("user");
		if (userObj != null && userObj instanceof UserEntity)
		{
			user = (UserEntity) userObj;
		}
		return user;
	}

	private void storeUserInSession(UserEntity user)
	{
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute("user", user);
	}

	private void deleteUserFromSession()
	{
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute("user");
		UserEntity user = null;
		if (userObj != null && userObj instanceof UserEntity)
		{
			user = (UserEntity) userObj;
			user.setSessionId(null);
		}
		session.removeAttribute("user");
	}

}