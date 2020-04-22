package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.Info;

@Dao
public interface InfoDao {
    @Insert
    void insertInfo(Info... infos);

    @Update
    void updateInfo(Info... infos);

    @Delete
    void deleteInfo(Info... infos);

    @Query("SELECT * FROM INFO_CARD")
    LiveData<List<Info>> getInfoAll();
}
