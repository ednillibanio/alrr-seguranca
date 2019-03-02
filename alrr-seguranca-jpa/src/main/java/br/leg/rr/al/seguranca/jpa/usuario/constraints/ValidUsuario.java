package br.leg.rr.al.seguranca.jpa.usuario.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsuarioConstraintValidator.class)
@Documented
public @interface ValidUsuario {

	String message() default "Erro na validação dos dados da entidade Usuário.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
