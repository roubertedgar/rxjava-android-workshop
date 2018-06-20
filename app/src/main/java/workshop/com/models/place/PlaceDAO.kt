package workshop.com.models.place

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)

    @Query("SELECT * FROM Place")
    fun getAll(): Flowable<List<Place>>
}