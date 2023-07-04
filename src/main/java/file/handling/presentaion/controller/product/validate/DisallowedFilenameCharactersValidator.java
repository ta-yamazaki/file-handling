package file.handling.presentaion.controller.product.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisallowedFilenameCharactersValidator implements ConstraintValidator<DisallowedFilenameCharacters, MultipartFile> {
    private String[] disallowedCharacters;

    @Override
    public void initialize(DisallowedFilenameCharacters constraintAnnotation) {
        this.disallowedCharacters = constraintAnnotation.characters();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        String filename = multipartFile.getOriginalFilename();

        List<String> characters = Arrays.stream(disallowedCharacters)
                .map(character -> Pattern.quote(character)).toList();

        String regex = String.join("|", characters);
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(filename);
        return !matcher.find();
    }
}
