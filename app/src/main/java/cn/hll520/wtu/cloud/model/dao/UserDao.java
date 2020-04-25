package cn.hll520.wtu.cloud.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.hll520.wtu.cloud.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... users);

    @Update
    void updataUser(User... users);

    @Delete
    void deleteUser(User... users);

    @Query("SELECT * FROM USER_LOGIN")
    LiveData<List<User>> getUserAll();

    @Query("SELECT * FROM USER_LOGIN WHERE login=:login")
    User getUser_login(int login);

    @Query("SELECT * FROM USER_LOGIN WHERE UID=:UID")
    User getUser_UID(int UID);


}
