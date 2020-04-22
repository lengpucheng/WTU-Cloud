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
    private int tMin=1;//开始节
    @ColumnInfo
    private int tMax=1;//结束节
    @ColumnInfo
    private int wMin=1;//开始周
    @ColumnInfo
    private int wMax=1;//结束周
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

    public int gettMin() {
        return tMin;
    }

    public void settMin(int tMin) {
        this.tMin = tMin;
    }

    public int gettMax() {
        return tMax;
    }

    public void settMax(int tMax) {
        this.tMax = tMax;
    }

    public int getwMin() {
        return wMin;
    }

    public void setwMin(int wMin) {
        this.wMin = wMin;
    }

    public int getwMax() {
        return wMax;
    }

    public void setwMax(int wMax) {
        this.wMax = wMax;
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
}
