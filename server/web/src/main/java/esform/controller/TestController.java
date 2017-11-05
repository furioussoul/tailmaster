package esform.controller;

import esform.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @PostMapping("submit_form")
    @ResponseBody
    public Response submit_form(@RequestBody Map map) {
        System.out.println(map);
        return Response.ok(map);
    }
}
