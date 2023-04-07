package leoguedex.com.github.API_Sales_Java.validation;

import leoguedex.com.github.API_Sales_Java.validation.constraintValidation.NotEmptyListValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default  "List cannot be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
