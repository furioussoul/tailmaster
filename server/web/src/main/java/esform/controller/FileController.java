package esform.controller;

import esform.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/upload_file")
    @ResponseBody
    public Response uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return Response.ok("不要上传空的文件");
        }

//        String randomPrefix = String.valueOf(DateUtils.now().getTime()).substring(0, 6);
//        String fileName = randomPrefix + multipartFile.getOriginalFilename();


        return Response.ok("http://abc.jpg");
    }
}
