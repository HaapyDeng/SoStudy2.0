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
        data = data.replaceFirst("<p>", "<p>(A)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(B)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(C)");
        data = data.replaceFirst("</p>,<p>", "</p><p>(D)");
        return data;
    }
}
