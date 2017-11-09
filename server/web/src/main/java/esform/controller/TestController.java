package esform.controller;

import esform.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @PostMapping("refreshTable")
    @ResponseBody
    public Response submit_form(@RequestBody Map map) {
        List<Map> list= new ArrayList<>();
        for(int i =0;i<10;i++){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name","xm");
            hashMap.put("age","18");
            hashMap.put("gender","male");
            list.add(hashMap);
        }
        return Response.ok(list);
    }
}
