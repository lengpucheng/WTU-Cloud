package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "INFO_CARD")
public class Info {
    @PrimaryKey(autoGenerate = true)
    private int _id;    //编号
    @ColumnInfo
    private int POST_ID=0;  //发送者ID
    @ColumnInfo
    private int GIT_ID=0;   //接收者ID
    @ColumnInfo
    private int infoclass=0;    //消息类型
    @ColumnInfo
    private String var="";  //内容
    @ColumnInfo
    private String time=""; //时间

    public Info(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPOST_ID() {
        return POST_ID;
    }

    public void setPOST_ID(int POST_ID) {
        this.POST_ID = POST_ID;
    }

    public int getGIT_ID() {
        return GIT_ID;
    }

    public void setGIT_ID(int GIT_ID) {
        this.GIT_ID = GIT_ID;
    }

    public int getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(int infoclass) {
        this.infoclass = infoclass;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
