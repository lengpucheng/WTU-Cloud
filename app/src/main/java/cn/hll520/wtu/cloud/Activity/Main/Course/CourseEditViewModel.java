package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.repository.CourseRepository;

public class CourseEditViewModel extends AndroidViewModel {
    private CourseRepository courseRepository;
    private int id;
    private int UID;

    public CourseEditViewModel(@NonNull Application application) {
        super(application);
        courseRepository=new CourseRepository(application);
    }

    //添加
    void addCourse(Course course){courseRepository.addCourse(course);}

    //更新
    void updataCourse(Course course){courseRepository.updataCourse(course);}

    //获取




}
