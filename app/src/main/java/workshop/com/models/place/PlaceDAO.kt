package workshop.com.models.place

import androidx.room.*

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)
}