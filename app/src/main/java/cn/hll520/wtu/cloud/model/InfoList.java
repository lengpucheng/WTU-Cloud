package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "INFO_LIST")
public class InfoList {
    @ColumnInfo
    private int who = 0;//用户ID
    @ColumnInfo
    private int UID = 0;//UID
    @ColumnInfo
    private int count = 0;//未读消息数
    @ColumnInfo
    private String var = "";//消息概要
    @ColumnInfo
    private String inputBox = "";//输入框消息
    @ColumnInfo
    private String time = "";//最后一条消息时间


    public InfoList() {
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        if (var.length() <= 25)
            this.var = var;
        else
            this.var = var.substring(0, 24) + "...";
    }

    public String getInputBox() {
        return inputBox;
    }

    public void setInputBox(String inputBox) {
        this.inputBox = inputBox;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
