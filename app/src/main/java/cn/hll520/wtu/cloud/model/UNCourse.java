package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName ="COURSE_NULL")
public class UNCourse {
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

    public UNCourse(){}

    @Ignore
    public UNCourse(int week,int UID){this.week=week;this.UID=UID;}

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

    @Override
    public String toString() {
        return "UNCourse{" +
                "_id=" + _id +
                ", UID=" + UID +
                ", SID='" + SID + '\'' +
                ", name='" + name + '\'' +
                ", week=" + week +
                ", am1_2='" + am1_2 + '\'' +
                ", am3_5='" + am3_5 + '\'' +
                ", pm1_2='" + pm1_2 + '\'' +
                ", pm3_4='" + pm3_4 + '\'' +
                ", night='" + night + '\'' +
                '}';
    }

    /*
    * ————————————————————————其他方法——————————————————————————
    * */

    //初始化
    @Ignore
    public static List<UNCourse> getDeft(int uid){
        List<UNCourse> unCourses=new ArrayList<>();
        unCourses.add(new UNCourse(1,uid));
        unCourses.add(new UNCourse(2,uid));
        unCourses.add(new UNCourse(3,uid));
        unCourses.add(new UNCourse(4,uid));
        unCourses.add(new UNCourse(5,uid));
        unCourses.add(new UNCourse(6,uid));
        unCourses.add(new UNCourse(7,uid));
        return unCourses;
    }

    //解析
    @Ignore
    public int[] getMax_Min(int i){
        int[] Max_Min={0,0};
        switch (i){
            case 0:
                String[] am1=getAm1_2().split("-");
                Max_Min=new int[]{Integer.parseInt(am1[0]), Integer.parseInt(am1[1])};
                break;
            case 1:
                String[] am2=getAm3_5().split("-");
                Max_Min=new int[]{Integer.parseInt(am2[0]), Integer.parseInt(am2[1])};
                break;
            case 2:
                String[] pm1=getPm1_2().split("-");
                Max_Min=new int[]{Integer.parseInt(pm1[0]), Integer.parseInt(pm1[1])};
                break;
            case 3:
                String[] pm2=getPm3_4().split("-");
                Max_Min=new int[]{Integer.parseInt(pm2[0]), Integer.parseInt(pm2[1])};
                break;
            case 4:
                String[] ni=getNight().split("-");
                Max_Min=new int[]{Integer.parseInt(ni[0]), Integer.parseInt(ni[1])};
                break;
        }
        return Max_Min;
    }


}
