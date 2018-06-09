package workshop.com.models

import android.content.Context
import androidx.room.Room
import workshop.com.models.place.PlaceDAO


class FactoryDAO {
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private fun getAppDatabase(applicationContext: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: newInstance(applicationContext).also { INSTANCE = it }
                }

        private fun newInstance(applicationContext: Context) =
                Room.databaseBuilder(applicationContext,
                        AppDatabase::class.java, "workshop.db").build()


        fun getPlaceDatabase(applicationContext: Context): PlaceDAO {
            return getAppDatabase(applicationContext).placeDAO()
        }
    }
}
