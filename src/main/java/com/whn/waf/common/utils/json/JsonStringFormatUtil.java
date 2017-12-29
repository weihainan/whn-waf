package com.whn.waf.common.utils.json;


import com.whn.waf.common.support.WafJsonMapper;
import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/4/19.
 */
public class JsonStringFormatUtil {

    /**
     * 打印输入到控制台
     */
    public static void printJson(String jsonStr) {
        System.out.println(formatJson(jsonStr));
    }

    /**
     * 验证字符串是否符合json格式 较为宽松
     */
    public static boolean valid(String jsonStr) {
        try {
            JSONObject.fromObject(jsonStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证字符串是否符合json格式 严格的审查 单引号不行
     */
    public static boolean validStrict(String jsonStr) {
        try {
            WafJsonMapper.parse(jsonStr, HashMap.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 格式化
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static void main(String[] args) {

        String str = "{\"content\":\"this is the msg content.\",\"tousers\":\"user1|user2\",\"msgtype\":\"texturl\",\"appkey\":\"test\",\"domain\":\"test\","
                + "\"system\":{\"wechat\":{\"safe\":\"1\"}},\"texturl\":{\"urltype\":\"0\",\"user1\":{\"spStatus\":\"user01\",\"workid\":\"work01\"},\"user2\":{\"spStatus\":\"user02\",\"workid\":\"work02\"}}}";
        JsonStringFormatUtil.printJson(str);
        System.out.println(JsonStringFormatUtil.valid(str));

        String jsonStr = "{\"website\":\"oschina.net\",'name':'123'}";
        System.out.println(JsonStringFormatUtil.valid(jsonStr));
        JsonStringFormatUtil.printJson(jsonStr);
    }
}
