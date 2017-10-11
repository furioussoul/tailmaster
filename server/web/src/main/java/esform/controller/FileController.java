package esform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("/file")
public class FileController {
   /* @Resource
    private Map<String, String> properties;
    @Autowired
    private FileManager fileManager;

    @RequestMapping("/upload_file")
    @ResponseBody
    public Response uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return Response.ok("不要上传空的文件");
        }

        String randomPrefix = String.valueOf(DateUtils.now().getTime()).substring(0, 6);
        String fileName = randomPrefix + multipartFile.getOriginalFilename();

        boolean success = fileManager.putObjByStream(properties.get("bucket"), fileName, multipartFile.getBytes());
        if (!success) {
            return Response.fail("文件上传失败");
        }

        String fileUrl = fileManager.getURL(properties.get("bucket"), fileName);
        return Response.ok(fileUrl);
    }*/
}
