package file.handling.presentaion.controller.product.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class AllowedFileFormatValidator implements ConstraintValidator<AllowedFileFormatBinary, MultipartFile> {
    String[] allowedFileFormats;

    @Override
    public void initialize(AllowedFileFormatBinary constraintAnnotation) {
        this.allowedFileFormats = constraintAnnotation.formats();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext context) {
        try {
            char[] fileHexChars = Hex.encodeHex(multipartFile.getBytes(), false);

            for (String formatString : allowedFileFormats) {
                FileFormatType format = FileFormatType.of(formatString);
                if (format.sameHeadHexChars(fileHexChars)) return true;
            }

            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public enum FileFormatType {
        JPEG("FFD8".toUpperCase().toCharArray()),
//        JPEG("FFD8DDE0".toUpperCase().toCharArray()),
//        JPG("FFD8FFEE".toUpperCase().toCharArray()),
        PNG("89504E47".toUpperCase().toCharArray())
        ;

        final char[] headHexCharacters;

        FileFormatType(char[] headHexString) {
            this.headHexCharacters = headHexString;
        }

        char[] headHexCharacters() {
            return headHexCharacters;
        }

        int length() {
            return headHexCharacters.length;
        }

        static FileFormatType of(String format) throws IllegalArgumentException {
            if (format.equalsIgnoreCase("jpg")) return JPEG;
            return FileFormatType.valueOf(format.toUpperCase());
        }

        boolean sameHeadHexChars(char[] targetHexChars) {
            char[] fileHeadHexChars = Arrays.copyOf(targetHexChars, length());
//            char[] upperCase = new String(fileHeadHexChars).toUpperCase().toCharArray();
            return Arrays.equals(headHexCharacters(), fileHeadHexChars);
        }
    }
}
