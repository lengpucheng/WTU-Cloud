package cn.hll520.wtu.cloud.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cn.hll520.wtu.cloud.model.Info;
import cn.hll520.wtu.cloud.model.InfoList;
import cn.hll520.wtu.cloud.model.dao.InfoDao;
import cn.hll520.wtu.cloud.model.dao.InfoListDao;

@Database(entities = {Info.class, InfoList.class},version =1,exportSchema = false)
public abstract class InfoDatabase extends RoomDatabase {

    private static InfoDatabase DB_INFO;
    //永远只执行一次
    static synchronized InfoDatabase getDatabase(Context context) {
        if (DB_INFO == null) {
            //数据库名
            DB_INFO = Room.databaseBuilder(context.getApplicationContext(), InfoDatabase.class, "info_data")
//                    .addMigrations(MIGRATION_17_18) //升级
//                    .fallbackToDestructiveMigration() //强制升级
                    .build();
        }
        return DB_INFO;
    }

    public abstract InfoDao getInfoDao();

    public abstract InfoListDao getInfoListDao();
}
