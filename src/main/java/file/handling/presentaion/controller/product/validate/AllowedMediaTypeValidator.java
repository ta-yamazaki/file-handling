package file.handling.presentaion.controller.product.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class AllowedMediaTypeValidator implements ConstraintValidator<AllowedMediaType, MultipartFile> {
    private String[] allowedMediaTypes;

    @Override
    public void initialize(AllowedMediaType constraintAnnotation) {
        this.allowedMediaTypes = constraintAnnotation.mediaTypes();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        String mediaType = multipartFile.getContentType();
//        ImageIO.read(multipartFile);
        return Arrays.asList(allowedMediaTypes).contains(mediaType);
    }
}
