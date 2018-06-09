package workshop.com.models

import androidx.room.Database
import androidx.room.RoomDatabase
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO

@Database(entities = [Place::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
}