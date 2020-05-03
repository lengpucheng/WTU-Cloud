package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.repository.CourseRepository;

public class CourseEditViewModel extends AndroidViewModel {
    private CourseRepository courseRepository;
    private Course course=new Course();
    private boolean isUPData=false;
    int UID=0;

    public CourseEditViewModel(@NonNull Application application) {
        super(application);
        courseRepository=new CourseRepository(application);
    }
    //设置对象
    void setCourse(Course course){this.course=course;}
    //获取对象
    Course getCourse(){return course;}
    //设置是否是更改
    void setUPData(boolean isUPData){this.isUPData=isUPData;UID=course.getWho();}

    //添加
    void addCourse(Course course){courseRepository.addCourse(course);}

    //更新
    void updataCourse(Course course){courseRepository.updataCourse(course);}

    //保存
    void saveCourse(){
        course.setWho(UID);
        if(isUPData)
            updataCourse(course);
        else
            addCourse(course);
    }




}
