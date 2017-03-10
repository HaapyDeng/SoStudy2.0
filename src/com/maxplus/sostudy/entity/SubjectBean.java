package com.maxplus.sostudy.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SubjectBean extends DataSupport {

    //试卷的id
    private String papersid;

    //题目的内容
    private String content;

    //题目的选项
    private String alternative;

    // 小题排序
    private String quesn;

    //题目类型
    private String type;

    //题目的正确答案
    private String success;

    //题目的id
    private String id;

    //题目的答案
    private String answer;

    //題目正确与否
    private String yorn;


    public String getPapersid() {
        return papersid;
    }

    public void setPapersid(String papersid) {
        this.papersid = papersid;
    }

//    //下拉选择条目
//    private String back;

//    //题目的回答总数
//    private String thetotal;
//
//    //题目的回答正确数
//    private String therightv;


    public String getYorn() {
        return yorn;
    }

    public void setYorn(String yorn) {
        this.yorn = yorn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getQuesn() {
        return quesn;
    }

    public void setQuesn(String quesn) {
        this.quesn = quesn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

//    public String getBack() {
//        return back;
//    }
//
//    public void setBack(String back) {
//        this.back = back;
//    }

//    public String getThetotal() {
//        return thetotal;
//    }
//
//    public void setThetotal(String thetotal) {
//        this.thetotal = thetotal;
//    }
//
//    public String getTherightv() {
//        return therightv;
//    }
//
//    public void setTherightv(String therightv) {
//        this.therightv = therightv;
//    }

    //jisusocaoshi mai hot nisjniuh one more line nishia but no songe nbad  +sdghh h@jhsd gytg-*(hsadgvmisjjijshi
}
