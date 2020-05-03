package cn.hll520.wtu.cloud.util;




import java.util.List;

import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.model.UNCourse;

public class CreateUNCourse {
    private List<UNCourse> unCourses;

    public CreateUNCourse( List<Course> courses, int UID){
        //初始化一个空课表
        List<UNCourse> unCourses=UNCourse.getDeft(UID);
        //写入课程数据
        for(Course course:courses)
            setTime(unCourses.get(course.getWeek()-1),course);
        this.unCourses=unCourses;
    }

    //拼合课程
    private void setTime(UNCourse unCourse, Course course) {
        //如果上课时间是1-2就写入
        if(course.getTmin()<3)
            unCourse.setAm1_2(course.getWmin()+"-"+course.getWmax());
        //如果下课时间在3——5写入
        if(course.getTmax()>2&&course.getTmax()<=5)
            unCourse.setAm3_5(course.getWmin()+"-"+course.getWmax());
        //如果上课时间在6-7
        if(course.getTmin()>5&&course.getTmin()<8)
            unCourse.setPm1_2(course.getWmin()+"-"+course.getWmax());
        //如果下课时间在8-9
        if(course.getTmax()>7&&course.getTmax()<10)
            unCourse.setPm3_4(course.getWmin()+"-"+course.getWmax());
        //如果是晚上上课
        if(course.getTmin()>9)
            unCourse.setNight(course.getWmin()+"-"+course.getWmax());
    }

    //获取返回的空课表
   public List<UNCourse> getUnCourses(){return unCourses;}

}
