package esform.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import esform.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szj on 2018/5/3.
 */
@Controller
@RequestMapping("/tf")
public class TfController {

    @PostMapping("ask")
    @ResponseBody
    public static Response ask() throws InvalidProtocolBufferException {
        SavedModelBundle savedModelBundle = SavedModelBundle.load("server/web/src/main/resources/module","bot");
        long[][] matrix = new long[20][1];
        for(int i = 0;i<20;i++){
            matrix[i][0] = 1;
        }
        long[][] matrix2 = new long[20][1];
        for(int i = 0;i<20;i++){
            matrix[i][0] = 1;
        }
        Tensor<Long> x = Tensor.create(matrix, Long.class);
        Tensor<Long> o = Tensor.create(matrix2, Long.class);
        SignatureDef sig = MetaGraphDef.parseFrom(savedModelBundle.metaGraphDef()).getSignatureDefOrThrow("signature");
        String inputName = sig.getInputsMap().get("input").getName();
        System.out.println(inputName);
        String outputName = sig.getOutputsMap().get("output").getName();
        System.out.println(outputName);
        List<Tensor<?>> y = savedModelBundle.session().runner().feed(inputName, x).fetch(outputName).run();
        long [][] result = new long[20][1];
        System.out.println(y.get(0).dataType());
        System.out.println(y.get(0).copyTo(result));
        System.out.println(result[0][0]);
        return Response.ok();
    }

    public static void main(String[] arg) throws InvalidProtocolBufferException {
        ask();
    }
}
