package com.example.finalprojectgithubuser.room



import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.finalprojectgithubuser.model.User



@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = REPLACE)
    fun insert(user: User): Long

    @Delete
    fun deleteUser(user: User): Int
}