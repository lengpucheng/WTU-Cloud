package cn.hll520.wtu.cloud.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.UNCourse;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.model.dao.CourseDao;
import cn.hll520.wtu.cloud.model.dao.PeopleDao;
import cn.hll520.wtu.cloud.model.dao.UNCourseDao;
import cn.hll520.wtu.cloud.model.dao.UserDao;

@Database(entities = {User.class, People.class, Course.class, UNCourse.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase DB_PEO;

    //永远只执行一次
    static synchronized UserDatabase getDatabase(Context context) {
        if (DB_PEO == null) {
            //数据库名
            DB_PEO = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "peo_data")
//                    .addMigrations(MIGRATION_17_18) //升级
//                    .fallbackToDestructiveMigration() //强制升级
                    .build();
        }
        return DB_PEO;
    }

    public abstract PeopleDao getPeoDao();

    public abstract UserDao getUserDao();

    public abstract CourseDao getCourseDao();

    public abstract UNCourseDao getUNCourseDao();
}
