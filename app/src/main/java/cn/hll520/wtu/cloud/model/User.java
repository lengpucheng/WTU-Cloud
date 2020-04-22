package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "USER_LOGIN")
public class User {

    @ColumnInfo(name="login")
    private boolean login=false;//是否登录
    @ColumnInfo(name="UID")
    private int UID=0;          //UID
    @ColumnInfo(name="SID")
    private String SID="";      //学号
    @ColumnInfo(name = "uname")
    private String uname="";    //用户名
    @ColumnInfo(name = "password")
    private String password="";   //密码
    @ColumnInfo(name = "passinfo")
    private String passinfo=""; //密保
    @ColumnInfo(name = "passkey")
    private String passkey="";      //密保答案
    @ColumnInfo(name = "regTime")
    private String regTime="2020-04-20"; //注册时间
    @ColumnInfo(name = "read")
    private int read=0;         //消息记录
    @ColumnInfo(name = "userOrg")
    private int userOrg=0;      //所在组织
    @ColumnInfo(name = "userMent")
    private int userMent=0;     //所在部门
    @ColumnInfo(name = "userSafety")
    private int userSafety=0;   //安全等级

    public User(){}

    public User(int UID, String password) {
        this.UID = UID;
        this.password = password;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassinfo() {
        return passinfo;
    }

    public void setPassinfo(String passinfo) {
        this.passinfo = passinfo;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }


    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(int userOrg) {
        this.userOrg = userOrg;
    }

    public int getUserMent() {
        return userMent;
    }

    public void setUserMent(int userMent) {
        this.userMent = userMent;
    }

    public int getUserSafety() {
        return userSafety;
    }

    public void setUserSafety(int userSafety) {
        this.userSafety = userSafety;
    }


    @Override
    public String toString() {
        return "User{" +
                "login=" + login +
                ", UID=" + UID +
                ", SID='" + SID + '\'' +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", passinfo='" + passinfo + '\'' +
                ", passkey='" + passkey + '\'' +
                ", regTime='" + regTime + '\'' +
                ", read=" + read +
                ", userOrg=" + userOrg +
                ", userMent=" + userMent +
                ", userSafety=" + userSafety +
                '}';
    }
}
