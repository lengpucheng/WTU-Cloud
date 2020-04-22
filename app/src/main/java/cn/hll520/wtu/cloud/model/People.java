package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PEOPLE_INFO")
public class People {
    @ColumnInfo
    private int who=0;   //属于谁
    @PrimaryKey
    private int UID=0;   //UID
    @ColumnInfo
    private int SID=0;  //学号
    @ColumnInfo
    private String uname="";    //用户名
    @ColumnInfo
    private String img="101";   //头像
    @ColumnInfo
    private String name="未设置";  //姓名
    @ColumnInfo
    private String gender="男";  //性别
    @ColumnInfo
    private String birthday="1999-01-01";   //生日
    @ColumnInfo
    private String phone="860";     //手机
    @ColumnInfo
    private String QQ="未设置";    //QQ
    @ColumnInfo
    private String Email="未设置"; //电子邮件
    @ColumnInfo
    private String campus="阳光校区";   //校区
    @ColumnInfo
    private String college="保密";       //学院
    @ColumnInfo
    private String clas="保密";      //班级
    @ColumnInfo
    private String house="保密";      //宿舍
    @ColumnInfo
    private String mainOrg="";      //主要组织
    @ColumnInfo
    private String mainMent="";  //主要部门
    @ColumnInfo
    private String mainPositior; //主要职务
    @ColumnInfo
    private String regTime="2020-04-20"; //注册时间
    @ColumnInfo
    private boolean login=false;  //是否在线

    public People(){}

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

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getMainOrg() {
        return mainOrg;
    }

    public void setMainOrg(String mainOrg) {
        this.mainOrg = mainOrg;
    }

    public String getMainMent() {
        return mainMent;
    }

    public void setMainMent(String mainMent) {
        this.mainMent = mainMent;
    }

    public String getMainPositior() {
        return mainPositior;
    }

    public void setMainPositior(String mainPositior) {
        this.mainPositior = mainPositior;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setLogin(int isLogin){
        this.login=(isLogin==1);
    }
}
