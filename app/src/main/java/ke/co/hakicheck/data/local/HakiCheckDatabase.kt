package ke.co.hakicheck.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedClaimEntity::class], version = 1, exportSchema = false)
abstract class HakiCheckDatabase : RoomDatabase() {

    abstract fun savedClaimDao(): SavedClaimDao

    companion object {
        @Volatile
        private var INSTANCE: HakiCheckDatabase? = null

        fun getDatabase(context: Context): HakiCheckDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HakiCheckDatabase::class.java,
                    "hakicheck.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
