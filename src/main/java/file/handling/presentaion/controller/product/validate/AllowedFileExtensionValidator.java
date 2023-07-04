package file.handling.presentaion.controller.product.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class AllowedFileExtensionValidator implements ConstraintValidator<AllowedFileExtension, MultipartFile> {
    private String[] allowedFileExtensions;

    @Override
    public void initialize(AllowedFileExtension constraintAnnotation) {
        this.allowedFileExtensions = constraintAnnotation.extensions();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return Arrays.asList(allowedFileExtensions).contains(extension);
    }
}
