package br.leg.rr.al.seguranca.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.FacesUtils;
import br.leg.rr.al.seguranca.domain.SegurancaValidationMessages;

@FacesValidator(value = "confirmaSenhaValidator")
public class ConfirmaSenhaValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		String confirmaSenha = (String) value;
		String novaSenha = null;
		UIInput compConfirmaSenha = (UIInput) FacesUtils.findComponent("novaSenha");

		if (compConfirmaSenha != null) {
			if (novaSenha == null) {
				novaSenha = (String) compConfirmaSenha.getSubmittedValue();
			}
			if (novaSenha == null) {
				novaSenha = (String) compConfirmaSenha.getValue();
			}
		}

		if (StringUtils.isNotBlank(confirmaSenha) && org.apache.commons.lang3.StringUtils.isNotBlank(novaSenha)) {
			if (!confirmaSenha.equals(novaSenha)) {

				String msg = SegurancaValidationMessages.CONFIRMA_SENHA_DIFERENTE_DE_SENHA;
				throw new ValidatorException(FacesMessageUtils.createError(msg));
			}
		}

	}
}
