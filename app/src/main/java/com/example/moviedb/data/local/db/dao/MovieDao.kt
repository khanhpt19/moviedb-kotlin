package com.example.moviedb.data.local.db.dao

import androidx.room.*
import com.example.moviedb.data.model.Movie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie?)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: String?): Single<Movie>

    @Query("SELECT * FROM movie")
    fun getMoviesLocal(): Single<List<Movie>>

    @Delete
    fun removeMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    fun removeMovie(id: String?)
}
