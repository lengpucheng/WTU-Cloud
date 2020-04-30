package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.Course;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(Course... courses);

    @Update
    void updateCourse(Course... courses);

    @Delete
    void deleteCourse(Course... courses);

    @Query("SELECT * FROM COURSE_TABLE")
    LiveData<List<Course>> getCourseAll();

    @Query("SELECT * FROM COURSE_TABLE WHERE who=:who")
    LiveData<List<Course>> getCourseWho(int who);
    //删除全部
    @Query("DELETE FROM COURSE_TABLE WHERE who=:who")
    void deleAllWho(int who);
    //获取通过_id
    @Query("SELECT * FROM COURSE_TABLE WHERE _id=:id")
    LiveData<Course> findCourse(int id);
}
