package file.handling.presentaion.controller.product.validate;

import file.handling.application.service.product.ProductRepository;
import file.handling.domain.model.product.ImageFileMetadata;
import file.handling.domain.model.product.ProductImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class DisallowedSameFileValidator implements ConstraintValidator<DisallowedSameFile, MultipartFile> {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void initialize(DisallowedSameFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        boolean existsFilename = productRepository.productExistsOf(multipartFile.getOriginalFilename());
        if (!existsFilename) return true;

        try {
            ImageFileMetadata targetMetadata = ImageFileMetadata.from(multipartFile);
            ImageFileMetadata metadata = productRepository.fileMetadataOf(multipartFile.getOriginalFilename());
            return !metadata.isSame(targetMetadata);
        } catch (IOException | NoSuchAlgorithmException e) {
            return false;
        }
    }
}
