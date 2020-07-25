package com.example.finalprojectgithubuser.room



import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.finalprojectgithubuser.model.User



@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM User")
    fun getUserCursor(): Cursor

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): User

    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Delete
    fun deleteUser(user: User) : Int

    @Query("SELECT * FROM user WHERE id = :id")
    fun deleteUserById(id: Long): Int
}