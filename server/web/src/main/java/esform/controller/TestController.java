package esform.controller;

import esform.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("test")
public class TestController {

    @PostMapping("userList")
    @ResponseBody
    public Response userList(@RequestBody Map map) {
        System.out.println(map);
        Map<String,Object> user = new HashMap<>();
        user.put("name","jinsanpang");
        user.put("age","30");
        user.put("address","china");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(user);
        return Response.ok(list);
    }
}
