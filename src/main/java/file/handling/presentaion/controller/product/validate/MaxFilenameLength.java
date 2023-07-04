package file.handling.presentaion.controller.product.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MaxFilenameLengthValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFilenameLength {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int max();
}
