package workshop.com.models.place


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Place(@PrimaryKey @ColumnInfo var name: String, @ColumnInfo var description: String) : Serializable