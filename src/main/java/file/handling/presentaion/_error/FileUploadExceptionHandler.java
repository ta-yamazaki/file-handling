package file.handling.presentaion._error;

import file.handling.domain.model.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FileUploadExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView maxUploadSizeException(MaxUploadSizeExceededException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "アップロードできるファイルサイズは" + maxFileSize + "です。");
        modelAndView.addObject("product", new Product());
        modelAndView.setViewName("product/add");
        return modelAndView;
    }
}
