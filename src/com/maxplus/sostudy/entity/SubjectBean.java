package com.maxplus.sostudy.entity;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SubjectBean {
    //题目的内容
    private String content;

    //题目的选项
    private String alternative;

    // 小题排序
    private String quesn;

    //题目类型
    private String type;

    //题目的正确答案
    private int success;

    //题目的id
    private String id;

    //题目的答案
    private String answer;

    //下拉选择条目
    private String back;

    //题目的回答总数
    private String thetotal;

    //题目的回答正确数
    private String therightv;

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

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
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

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getThetotal() {
        return thetotal;
    }

    public void setThetotal(String thetotal) {
        this.thetotal = thetotal;
    }

    public String getTherightv() {
        return therightv;
    }

    public void setTherightv(String therightv) {
        this.therightv = therightv;
    }

}
