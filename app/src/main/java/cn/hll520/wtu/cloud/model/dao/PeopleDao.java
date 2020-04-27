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
    void insertPeo(People... people);

    @Update
    void updatePeo(People... people);

    @Delete
    void deletePeo(People... people);

    @Query("SELECT * FROM PEOPLE_INFO")
    LiveData<List<People>> getPeoAll();

    @Query("SELECT * FROM PEOPLE_INFO WHERE _ID=:peo_id")
    People getPeoForID(int peo_id);

    @Query("DELETE FROM PEOPLE_INFO")
    void out_login();

    @Query("SELECT * FROM PEOPLE_INFO WHERE UID=:UID")
    People getPeoForUID(int UID);
}
