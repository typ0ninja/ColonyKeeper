package com.team13.colonykeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Yard::class, Hive::class], version = 1, exportSchema = false)
public abstract class ColonyRoomDatabase: RoomDatabase() {
    abstract fun yardDao(): YardDao
    abstract fun hiveDao(): HiveDao

    companion object {
        @Volatile
        private var INSTANCE: ColonyRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ColonyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ColonyRoomDatabase::class.java,
                    "yard_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(YardDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class YardDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.yardDao(), database.hiveDao())
                }
            }
        }

        suspend fun populateDatabase(yardDao: YardDao, hiveDao: HiveDao) {
            yardDao.deleteAll()
            //make 2 yards
            var yard = Yard(1, "Yard 1")
            yardDao.insert(yard)
            yard = Yard(2, "Yard 2")
            yardDao.insert(yard)

            //add to first yard
            var hive = Hive(5,"batman", 1)
            hiveDao.insert(hive)
            hive = Hive(6,"robin", 1)
            hiveDao.insert(hive)

            //add to second yard
            hive = Hive(7,"riddler", 2)
            hiveDao.insert(hive)
            hive = Hive(8,"harley", 2)
            hiveDao.insert(hive)
        }
    }
}