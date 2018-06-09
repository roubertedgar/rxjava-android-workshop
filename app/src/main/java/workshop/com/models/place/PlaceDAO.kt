package workshop.com.models.place

import androidx.room.*

@Dao
interface PlaceDAO {
    @Query("Select * from Place")
    fun getAll(): List<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg place: Place)

    @Delete
    fun delete(place: Place)
}