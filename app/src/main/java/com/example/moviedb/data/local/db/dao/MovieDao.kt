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

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun removeMovie(id: String?)

    @Query("SELECT * FROM movie LIMIT :pageSize OFFSET ((:pageIndex-1)*:pageSize) ")
    suspend fun getMoviesLocal(pageSize: Int, pageIndex: Int): List<Movie>?
}
