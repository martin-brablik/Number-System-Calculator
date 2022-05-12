package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAll () : List<HistoryItem>

    @Query("DELETE FROM history WHERE id=:pos")
    fun deleteAt(pos: Int)

    @Query("DELETE FROM history")
    fun deleteAll()

    @Insert
    fun insert(item: HistoryItem): Long

    @Delete
    fun delete(item: HistoryItem)

    @Update
    fun update(item: HistoryItem) : Int
}