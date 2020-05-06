package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.People;

@Dao
public interface PeopleDao {

    @Insert
    void insertPeo(People... people);//插入

    @Insert
    void insertPeo(List<People> people);

    @Update
    void updatePeo(People... people);//更新

    @Delete
    void deletePeo(People... people);//删除

    @Query("SELECT * FROM PEOPLE_INFO")
    LiveData<List<People>> getPeoAll();//获取全部

    @Query("SELECT * FROM PEOPLE_INFO WHERE _ID=:peo_id")
    People getPeoForID(int peo_id);//根据ID获取

    @Query("SELECT * FROM PEOPLE_INFO WHERE _ID=:peo_id")
    LiveData<People> getInfoForID(int peo_id);


    @Query("DELETE FROM PEOPLE_INFO")
    void out_login();//删除所有

    @Query("SELECT * FROM PEOPLE_INFO WHERE UID=:UID")
    LiveData<People> getInfoForUID(int UID);
}
