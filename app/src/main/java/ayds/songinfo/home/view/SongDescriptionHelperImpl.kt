package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(val dateFormatFactory: DateFormatFactory) : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release Date: ${song.createDate()}"
            else -> "Song not found"
        }
    }

    private fun SpotifySong.createDate(): String{
        val dateFactory = dateFormatFactory.getDateFormat(this.releaseDatePrecision, this.releaseDate)
        return dateFactory.createDate()
    }
}