package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [HistoryItem::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: AppDb? = null
        fun getDatabase(context: Context): AppDb {
            if (instance != null) {
                return instance as AppDb
            } else {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "Database"
                ).allowMainThreadQueries().build()
                return instance as AppDb
            }
        }
    }
}