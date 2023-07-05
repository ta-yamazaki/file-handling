package file.handling.presentaion._error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String exception(Exception exception, Model model) {
        if (exception.getClass() == AccessDeniedException.class) {
            model.addAttribute("errors", new Errors("この操作は許可されていません。"));
            return "_error/exception";
        }


        log(exception);
        model.addAttribute("errors", from(exception));
        return "_error/exception";
    }

    private void log(Exception exception) {
        logger.error("err", exception);
        logger.error("エラー");
        Throwable each = exception;
        while(each != null) {
            logger.error("原因");
            logger.error(each.getMessage());
            showStackTrace(each);
            each = each.getCause();
        }
    }

    private void showStackTrace(Throwable throwable) {
        StackTraceElement[] elements = throwable.getStackTrace();
        for (int index = 0 ; index < 3 ; index++ ) {
            StackTraceElement each = elements[index];
            if (each == null) break;
            logger.error(String.format("%s %s %s()",
                    each.getFileName(), each.getLineNumber(), each.getMethodName()));
        }
    }

    private Errors from(Exception exception) {
        Errors errors = new Errors();
        errors.addLine(exception.getMessage());
        errors.addLine(exception.getClass().getSimpleName());
        return errors;
    }
}
