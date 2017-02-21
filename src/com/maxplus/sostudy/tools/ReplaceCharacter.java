package com.maxplus.sostudy.tools;

import android.text.Html;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ReplaceCharacter {


    //替换mathml中的字符串
    public static String Replace(String data) {
        String newdata = null;
        if (data.contains("«")) {
            newdata = data.replaceAll("«", "<");
            data = newdata;
        }
        if (data.contains("»")) {
            newdata = data.replaceAll("»", ">");
            data = newdata;
        }
        if (data.contains("¨")) {
            newdata = data.replaceAll("¨", "\"");
            data = newdata;
        }
        return data;
    }

    //在<p>标签前面添加选项A，B，C，D
    public static String addChar(String data) {
//        String newData;
//        String target = "</p>,<p>";
//        String replacement = "</p>,<p>(D)";
        data = data.replaceFirst("<p>", "<p>(A)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(B)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(C)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(D)");
//        if (data == null || target == null || replacement == null) {
//            throw new NullPointerException();
//        }
//        int index = data.indexOf(target);
//        if (index == -1) {
//            throw new Exception("Not Foud.");
//        }
//        index = data.indexOf(target, index + target.length());
//        if (index == -1) {
//            throw new Exception("Not Foud.");
//        }
//        String str1 = data.substring(0, index);
//        String str2 = data.substring(index);
//        str2 = str2.replace(target, replacement);
//        newData = str1 + str2;
        return data;
    }
}
/*
                                         * 替换第二个匹配的目标子串
										 * @param str 源字符串。
										 * @param target 需要替换的目标子串。
										 * @param replacement 需要替换成的字符串。
										 * @return 将源字符串中出现的第二个target换成replacement后的字符串。
										 * @throws NullPointerException 当任一参数为空时。
										 * @throws Exception 找不到第二个匹配的字符串时。
    public static String replaceSecond(String str, String target,
                                       String replacement) throws NullPointerException, Exception {
        if (str == null || target == null || replacement == null)
            throw new NullPointerException();
        int index = str.indexOf(target);
        if (index == -1)
            throw new Exception("Not Found.");
        index = str.indexOf(target, index + target.length());
        if (index == -1)
            throw new Exception("Not Found.");
        String str1 = str.substring(0, index);
        String str2 = str.substring(index);
        str2 = str2.replaceFirst("sss", "hello");
        return str1 + str2;
    }
 **/