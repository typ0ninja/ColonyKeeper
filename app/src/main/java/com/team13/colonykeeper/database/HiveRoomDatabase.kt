package com.team13.colonykeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Hive::class], version = 1, exportSchema = false)
public abstract class HiveRoomDatabase: RoomDatabase() {
    abstract fun hiveDao(): HiveDao

    companion object {
        @Volatile
        private var INSTANCE: HiveRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HiveRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HiveRoomDatabase::class.java,
                    "hive_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(HiveDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class HiveDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.hiveDao())
                }
            }
        }

        suspend fun populateDatabase(hiveDao: HiveDao) {
            hiveDao.deleteAll()
            var hive = Hive(1, "Crystal Tower", "Yard 1")
            hiveDao.insert(hive)
            hive = Hive(2, "The Hive", "Yard 1")
            hiveDao.insert(hive)
        }
    }
}