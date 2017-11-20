package esform.advice;

import esform.dao.AdviceDao;
import esform.domain.Advice;
import esform.response.Response;
import esform.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by szj on 2017/11/20.
 */
@Controller()
@RequestMapping("advice")
public class AdviceController {

    @Autowired
    private AdviceDao adviceDao;

    @PostMapping("add")
    @ResponseBody
    public Response add(@RequestBody Advice request) {
        Util.trace(request, true);
        adviceDao.add(request);
        return Response.ok();
    }
}
