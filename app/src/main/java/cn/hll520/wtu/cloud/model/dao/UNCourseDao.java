package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.UNCourse;

@Dao
public interface UNCourseDao {
    @Insert
    void insertUNCourse(UNCourse... unCourses);

    @Update
    void updateUNCourse(UNCourse... unCourses);

    @Delete
    void deleteUNCourse(UNCourse... unCourses);

    @Query("SELECT * FROM COURSE_NULL")
    LiveData<List<UNCourse>> getUNCourseAll();
}
