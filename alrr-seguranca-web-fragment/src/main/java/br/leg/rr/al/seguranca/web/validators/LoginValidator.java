package br.leg.rr.al.seguranca.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;

@FacesValidator(value = "loginValidator")
public class LoginValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String LOGIN_PATTERN = "^[a-z0-9_.]{5,20}$";

	public LoginValidator() {
		pattern = Pattern.compile(LOGIN_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher((CharSequence) value);
		if (!matcher.matches()) {
			throw new ValidatorException(
					FacesMessageUtils.createError(SegurancaValidationMessages.USUARIO_LOGIN_FORA_DO_PADRAO_SEGURANCA));
		}
	}

}
