package ayds.songinfo.moredetails.fulllogic.data.localdb

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

    @Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}

@Entity
data class CardEntity(
    @PrimaryKey
    val artistName: String,
    val text: String,
    val url: String,
    val source: String
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: CardEntity)

    @Query("SELECT * FROM Cardentity WHERE artistName LIKE :artistName")
    fun getCardByArtistName(artistName: String): List<CardEntity>?

}