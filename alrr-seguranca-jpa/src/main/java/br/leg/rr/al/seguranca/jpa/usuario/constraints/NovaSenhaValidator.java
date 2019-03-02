package br.leg.rr.al.seguranca.jpa.usuario.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;
import br.leg.rr.al.seguranca.jpa.PolicitaSenha;
import br.leg.rr.al.seguranca.jpa.Senha;
import br.leg.rr.al.seguranca.jpa.Usuario;

//FIXME: Corrigir classe que stá passando por alteraçoes.
@Named
public class NovaSenhaValidator implements ConstraintValidator<SenhaConstraint, Usuario> {

	// (?=.*\d) deve ter dois digitos de 0-9;
	// (?=.*[a-z]) deve ter duas letras minusculas;
	// (?=.*[A-Z]) deve ter duas letras maiusculas;
	// . combina com qualquer condição anterior;
	// {6,20} a senha deve ter de 6 a 20 digitos.
	private static final String PASSWORD_PATTERN = "((?=.*\\d{2})(?=.*[a-z]{2})(?=.*[A-Z]{2}).{6,20})";
	private static Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	private PolicitaSenha politica;
	private Senha senha;
	private Usuario usuario;
	private String login;
	private String novaSenha;
	private ConstraintValidatorContext context;
	private Integer minDig;
	private Integer minMaiusculas;
	private Integer minSimbolos;
	private Integer minMinusculas;
	private Integer tamMin;
	private Integer tamMax;

	@Override
	public void initialize(SenhaConstraint constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	private Boolean minDigitos() {

		return true;
	}

	@Override
	public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
		senha = politica.getSenha();
		novaSenha = usuario.getSenha();

		minSimbolos = senha.getQtdeMinSimbolos();

		String senhaPattern = "((?=.*\\d{" + minDig + "})" + "(?=.*[a-z]{" + minMinusculas + "})" + "(?=.*[A-Z]{"
				+ minMaiusculas + "})" + ".{" + tamMin + "," + tamMax + "})";

		if (politica.getPermiteMesmaSenha())

		{

		}

		if (contemMinusculas()) {
			context.buildConstraintViolationWithTemplate(SegurancaValidationMessages.USUARIO_ERROR_SENHA_FRACA)
					.addPropertyNode("senha").addConstraintViolation();
		}

		this.context = context;

		// senha = usuario.getSenha();

		// FIXME
		// Verifica se senha é fraca, ou seja, fora do padrão.
		/**
		 * if (!pattern.matcher(senha).matches())
		 * 
		 * {
		 * 
		 * context.buildConstraintViolationWithTemplate(
		 * SecurityValidationMessages.USUARIO_ERROR_SENHA_FRACA)
		 * .addPropertyNode("senha").addConstraintViolation();
		 * 
		 * return false; }
		 **/

		/**
		 * TODO: Colocar as novas constraints. Foi removido as antigas que
		 * envolvia pessoa, ano e outros dados. Agora, vamos validar somente
		 * oque consta em PoliticaSeguranca.java
		 */
		return true;

	}

	private Boolean isQtdeMinDig() {
		// PASSWORD_PATTERN = "((?=.*\\d{"+ 2})" +
		// politica.getSenha().getQtdeMinDigitos();
		// FIXME: Corrigir
		return true;

	}

	/**
	 * Verifica se contém o login do usuário na senha.
	 * 
	 * @return true se tem login.
	 */
	private Boolean contemLogin() {
		// Se tem o login na senha
		// FIXME
		/**
		 * if (StringUtils.containsIgnoreCase(senha, login)) {
		 * context.buildConstraintViolationWithTemplate(
		 * SecurityValidationMessages.USUARIO_ERROR_SENHA_IGUAL_LOGIN)
		 * .addPropertyNode("senha").addConstraintViolation();
		 * 
		 * return true; }
		 **/

		return false;
	}

	/**
	 * Verifica se contém nome ou sobrenome do usuário na senha.
	 * 
	 * @return true se tem nome ou sobrenome.
	 *//*
		 * private Boolean contemNomeSobrenome() { if
		 * (!sistema.getSeguranca().getSenha().getPermiteNomeSobrenome() &&
		 * !StringUtils.isBlank(nome.trim())) { if
		 * (StringHelper.containsIgnoreCase(senha, nome, " ")) {
		 * 
		 * context.buildConstraintViolationWithTemplate(
		 * SecurityValidationMessages.USUARIO_ERROR_SENHA_CONTEM_PARTE_NOME).
		 * addPropertyNode("senha") .addConstraintViolation(); return true; } }
		 * return false; }
		 */

	/**
	 * 
	 * @return
	 */
	private Boolean isTamanhoValido() {
		tamMin = senha.getTamanhoMinimo();
		tamMax = senha.getTamanhoMaximo();

		String regex = ".{".concat(tamMin.toString().concat(",").concat(tamMax.toString().concat("})")));
		Pattern p1 = Pattern.compile(regex);
		Matcher matcher = p1.matcher(novaSenha);
		return matcher.matches();
	}

	/**
	 * 
	 * @return
	 */
	private Boolean contemDigitos() {
		minDig = senha.getQtdeMinDigitos();
		String regex = "(?=.*\\d{".concat(minDig.toString().concat("})"));
		Pattern p1 = Pattern.compile(regex);
		Matcher matcher = p1.matcher(novaSenha);
		return matcher.matches();

	}

	/**
	 * 
	 * @return
	 */
	private Boolean contemMaiusculas() {
		minMaiusculas = senha.getQtdeMinLetrasMaiusculas();

		String regex = "(?=.*[A-Z]{".concat(minMaiusculas.toString().concat("})"));
		Pattern p1 = Pattern.compile(regex);
		Matcher matcher = p1.matcher(novaSenha);

		return matcher.matches();

	}

	/**
	 * 
	 * @return
	 */
	private Boolean contemMinusculas() {
		minMinusculas = senha.getQtdeMinLetrasMinusculas();

		String regex = "(?=.*[a-z]{".concat(minMinusculas.toString().concat("})"));
		Pattern p1 = Pattern.compile(regex);
		Matcher matcher = p1.matcher(novaSenha);

		return matcher.matches();

	}

	private Boolean contemSimbolos() { // FIXME: Arrumar

		/**
		 * Integer qtdeSimbolos = sistema.getSeguranca().getSenha()
		 * .getQtdeMinSimbolos(); CharMatcher if (qtdeSimbolos > 0) { String
		 * regex = "(?=.*[~ @ # $ ^ & * ( ) - _ + = [ ] { } | \ , . ? :]{"
		 * .concat(qtdeSimbolos.toString().concat( "})")); Pattern p1 =
		 * Pattern.compile(regex); Matcher matcher = p1.matcher(senha);
		 * 
		 * return matcher.matches(); }
		 **/
		return true;

	}

}
