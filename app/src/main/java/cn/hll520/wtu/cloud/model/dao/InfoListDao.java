package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.InfoList;

@Dao
public interface InfoListDao {

    @Insert
    void insertInfoList(InfoList... infoLists);

    @Update
    void updateInfoList(InfoList... infoLists);

    @Delete
    void deleteInfoList(InfoList... infoLists);

    @Query("SELECT * FROM INFO_LIST")
    LiveData<List<InfoList>> getInfoListAll();
}
