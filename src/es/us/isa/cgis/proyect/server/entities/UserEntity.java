package es.us.isa.cgis.proyect.server.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import es.us.isa.cgis.proyect.shared.demographic.Actor;

@Entity
public class UserEntity extends Actor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2154110797350720858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long key;

	String login;
	String password;

	@Transient
	private String sessionId;

	public UserEntity() {

	}

	public UserEntity(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setSessionId(String id) {
		sessionId = id;
	}

	public String getSessionId() {
		return sessionId;
	}

}
