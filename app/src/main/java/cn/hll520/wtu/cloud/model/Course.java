package cn.hll520.wtu.cloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "COURSE_TABLE")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int _id=0;  //编号
    @ColumnInfo
    private int who=0;//用户id
    @ColumnInfo
    private String SID="0";  //学号
    @ColumnInfo
    private String name="";//课程名
    @ColumnInfo
    private String campus="" ;//校区
    @ColumnInfo
    private String room="";//教室
    @ColumnInfo
    private int week=1; //星期几
    @ColumnInfo
    private int tmin=1; //开始节
    @ColumnInfo
    private int tmax =1;//结束节
    @ColumnInfo
    private int wmin =1;//开始周
    @ColumnInfo
    private int wmax =1;//结束周
    @ColumnInfo
    private String teacher="";//老师
    @ColumnInfo
    private String job="";//职务
    @ColumnInfo
    private String test="";//考试方式

    public Course(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
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

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getTmin() {
        return tmin;
    }

    public void setTmin(int tmin) {
        this.tmin = tmin;
    }

    public int getTmax() {
        return tmax;
    }

    public void setTmax(int tmax) {
        this.tmax = tmax;
    }

    public int getWmin() {
        return wmin;
    }

    public void setWmin(int wmin) {
        this.wmin = wmin;
    }

    public int getWmax() {
        return wmax;
    }

    public void setWmax(int wmax) {
        this.wmax = wmax;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String showInfo(){
        return "课程："+name+"\n地点："+campus+room+"\n时间："+wmin+"-"+wmax+"周"+week
            +tmin+"-"+tmax+"节\n老师："+ teacher +job+"\n考试方式："+test;
    }
}
