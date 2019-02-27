package com.example.moviedb.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedb.data.model.Movie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: String): Single<Movie>
}
