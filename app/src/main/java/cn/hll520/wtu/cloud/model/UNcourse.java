package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="COURSE_NULL")
public class UNcourse {
    @PrimaryKey(autoGenerate =true)
    private int _id=0;//编号
    @ColumnInfo
    private int UID=0;//UID
    @ColumnInfo
    private String SID="0";//学号
    @ColumnInfo
    private String name="";//姓名
    @ColumnInfo
    private int week=1;//星期
    @ColumnInfo
    private String am1_2="0-0";//上午
    @ColumnInfo
    private String am3_5="0-0";//上午2
    @ColumnInfo
    private String pm1_2="0-0";//下午
    @ColumnInfo
    private String pm3_4="0-0";//下午2
    @ColumnInfo
    private String night="0-0";//晚上

    public UNcourse(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getAm1_2() {
        return am1_2;
    }

    public void setAm1_2(String am1_2) {
        this.am1_2 = am1_2;
    }

    public String getAm3_5() {
        return am3_5;
    }

    public void setAm3_5(String am3_5) {
        this.am3_5 = am3_5;
    }

    public String getPm1_2() {
        return pm1_2;
    }

    public void setPm1_2(String pm1_2) {
        this.pm1_2 = pm1_2;
    }

    public String getPm3_4() {
        return pm3_4;
    }

    public void setPm3_4(String pm3_4) {
        this.pm3_4 = pm3_4;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }
}
