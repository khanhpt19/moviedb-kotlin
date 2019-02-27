package com.example.moviedb.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    val id: String = "",

    @SerializedName("overview")
    @Expose
    val overview: String = "",

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "",

    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "",

    @SerializedName("video")
    @Expose
    val video: Boolean = false,

    @SerializedName("title")
    @Expose
    val title: String = "",

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Integer>?,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "",

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "",

    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "",

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0,

    @SerializedName("popularity")
    @Expose
    val popularity: Double = 0.0,

    @SerializedName("adult")
    @Expose
    val adult: Boolean = false,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        TODO("genreIds"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(overview)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(popularity)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
