package esform.util;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class CommonTool {

    public static Matcher getMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(text);
    }

    public static Document xmlParser(String text) {
        Document doc = null;
        StringReader sr = new StringReader(text);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 消息格式化
     */
    public static void msgFormatter(JSONObject d, String k) {
        d.put(k, d.getString(k).replace("<br/>", "\n"));
        emojiFormatter(d, k);
        // TODO 与emoji表情有部分兼容问题，目前暂未处理解码处理 d.put(k,
        // StringEscapeUtils.unescapeHtml4(d.getString(k)));
    }

    /**
     * 处理emoji表情
     */
    public static void emojiFormatter(JSONObject d, String k) {
        Matcher matcher = getMatcher("<span class=\"emoji emoji(.{1,10})\"></span>", d.getString(k));
        StringBuilder sb = new StringBuilder();
        String content = d.getString(k);
        int lastStart = 0;
        while (matcher.find()) {
            String str = matcher.group(1);
            if (str.length() == 6) {

            } else if (str.length() == 10) {

            } else {
                str = "&#x" + str + ";";
                String tmp = content.substring(lastStart, matcher.start());
                sb.append(tmp + str);
                lastStart = matcher.end();
            }
        }
        if (lastStart < content.length()) {
            sb.append(content.substring(lastStart));
        }
        if (sb.length() != 0) {
            d.put(k, EmojiParser.parseToUnicode(sb.toString()));
        } else {
            d.put(k, content);
        }
    }
}
