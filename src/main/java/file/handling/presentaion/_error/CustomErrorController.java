package file.handling.presentaion._error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * エラーページ
 */
@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping
    public String errors(
            HttpServletRequest servletRequest,
            Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(servletRequest);
        Map<String, Object> map = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        Errors errors = new Errors();

        summary(errors,map);

        Throwable throwable = errorAttributes.getError(webRequest);

        if (throwable != null ) logger.error(throwable.getMessage(), throwable);

        messagesFrom(errors, throwable);
        model.addAttribute("errors", errors);
        return "_error/error-page";
    }

    private void summary(Errors errors, Map<String, Object> map) {
        String message = String.format(
            "%s %s %s %s",
                map.get("status"),
                map.get("error"),
                map.get("path"),
                map.get("message")
                );
        errors.addLine(message);
    }

    private void messagesFrom(Errors errors, Throwable root) {
        Throwable each = root;
        while (each != null) {
            errors.addLine(each.getMessage());
            each = each.getCause();
        }
    }

    public String getErrorPath() {
        return "error";
    }
}
