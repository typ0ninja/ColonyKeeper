package com.team13.colonykeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Yard::class], version = 1, exportSchema = false)
public abstract class YardRoomDatabase: RoomDatabase() {
    abstract fun yardDao(): YardDao

    companion object {
        @Volatile
        private var INSTANCE: YardRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): YardRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YardRoomDatabase::class.java,
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
                    populateDatabase(database.yardDao())
                }
            }
        }

        suspend fun populateDatabase(yardDao: YardDao) {
            yardDao.deleteAll()
            var yard = Yard(1, "Yard 1")
            yardDao.insert(yard)
            yard = Yard(2, "Yard 2")
            yardDao.insert(yard)
        }
    }
}