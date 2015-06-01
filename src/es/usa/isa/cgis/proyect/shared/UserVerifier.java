package es.usa.isa.cgis.proyect.shared;

public class UserVerifier {

	// TODO: Incluir la verificacion de usuario y password compleja (numero
	// minimo de caracteres etc.)

	public static boolean esLoginValido(String login) {
		if (login == null || login.isEmpty()) {
			return false;
		}
		return true;
	}
	public static boolean esPasswordValido(String pass) {
		if (pass == null || pass.isEmpty()) {
			return false;
		}
		return true;
	}
}
