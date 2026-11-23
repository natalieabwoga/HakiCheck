package ke.co.hakicheck.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedClaimDao {

    @Query("SELECT * FROM saved_claims ORDER BY checkedAtMillis DESC")
    fun getAllSavedClaims(): LiveData<List<SavedClaimEntity>>

    @Query("SELECT * FROM saved_claims WHERE claimText LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%' ORDER BY checkedAtMillis DESC")
    fun searchSavedClaims(query: String): LiveData<List<SavedClaimEntity>>

    @Query("SELECT * FROM saved_claims WHERE verdictString = :verdict ORDER BY checkedAtMillis DESC")
    fun getClaimsByVerdict(verdict: String): LiveData<List<SavedClaimEntity>>

    @Query("SELECT * FROM saved_claims WHERE id = :id LIMIT 1")
    suspend fun getClaimById(id: String): SavedClaimEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM saved_claims WHERE id = :id)")
    suspend fun isClaimSaved(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClaim(claim: SavedClaimEntity)

    @Query("DELETE FROM saved_claims WHERE id = :id")
    suspend fun deleteClaimById(id: String)

    @Query("DELETE FROM saved_claims")
    suspend fun clearAll()
}
