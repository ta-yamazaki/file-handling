package file.handling.presentaion.controller.product.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {AllowedFileExtensionValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedFileExtension {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] extensions();
}
