package file.handling.presentaion.controller.product.validate;

import file.handling.application.service.product.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class NotAllowedSameFilenameValidator implements ConstraintValidator<DisallowedSameFilename, MultipartFile> {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void initialize(DisallowedSameFilename constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        String filename = multipartFile.getOriginalFilename();
        boolean filenameExists = productRepository.productExistsOf(filename);
        return !filenameExists;
    }
}
