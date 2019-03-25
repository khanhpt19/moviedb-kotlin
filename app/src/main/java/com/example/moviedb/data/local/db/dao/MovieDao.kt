package com.example.moviedb.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviedb.data.model.Movie

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie?)

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: String?): Movie?

    @Query("SELECT * FROM movie")
    suspend fun getMoviesLocal(): List<Movie>?

    @Delete
    suspend fun removeMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun removeMovie(id: String?)
}
