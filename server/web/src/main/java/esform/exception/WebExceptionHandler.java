package esform.exception;

import esform.response.Response;
import esform.response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
@ControllerAdvice(basePackages = {"esform"})
public class WebExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Response handleControllerException(HttpServletRequest request, Throwable ex) {
        LOGGER.error("WebExceptionHandler", ex);
        return Response.ok(Status.fail, "bug happens, please contact author");
    }
}
