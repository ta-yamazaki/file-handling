package file.handling.presentaion.controller.product.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxFilenameLengthValidator implements ConstraintValidator<MaxFilenameLength, MultipartFile> {
    private int max;

    @Override
    public void initialize(MaxFilenameLength constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        int length = multipartFile.getOriginalFilename().length();
        return length <= this.max;
    }
}
