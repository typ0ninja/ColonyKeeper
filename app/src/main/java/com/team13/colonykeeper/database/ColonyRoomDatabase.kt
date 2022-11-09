package com.team13.colonykeeper.database

import android.content.Context
import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Yard::class, Hive::class, Inspections::class, Scheduled::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class ColonyRoomDatabase: RoomDatabase() {
    abstract fun colonyDao(): ColonyDao

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
                    //populateDatabase(database.colonyDao())
                }
            }
        }

        suspend fun populateDatabase(colonyDao: ColonyDao) {
            colonyDao.deleteAllYard()
            colonyDao.deleteAllHive()
            //make 2 yards
            var yard = Yard(1, "Yard 1")
            colonyDao.insertYard(yard)
            yard = Yard(2, "Yard 2")
            colonyDao.insertYard(yard)

            //add to first yard
            var hive = Hive(5,"batman", 1)
            colonyDao.insertHive(hive)
            hive = Hive(6,"robin", 1)
            colonyDao.insertHive(hive)

            //add to second yard
            hive = Hive(7,"riddler", 2)
            colonyDao.insertHive(hive)
            hive = Hive(8,"harley", 2)
            colonyDao.insertHive(hive)
        }
    }
}