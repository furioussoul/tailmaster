package esform;

import esform.response.Response;
import esform.wx.Core;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by szj46941 on 2018/2/27.
 */
@Controller
@RequestMapping("/wx")
public class WxController {

    @RequestMapping("/generateUUID")
    @ResponseBody
    public Response generateUUID() throws IOException {
        Core core = new Core();
        return Response.ok(core.getUUid());
    }
}