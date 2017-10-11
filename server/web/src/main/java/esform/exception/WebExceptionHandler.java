package esform.exception;

import esform.response.Response;
import esform.response.Status;
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

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Response handleControllerException(HttpServletRequest request, Throwable ex) {
        return Response.ok(Status.fail, ex.getMessage());
    }
}
