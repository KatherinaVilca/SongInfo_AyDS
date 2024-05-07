package ayds.songinfo.moredetails.fulllogic.domain.repository

import ayds.songinfo.moredetails.fulllogic.domain.entity.Artist

interface ArtistRepository {
    fun getArtist(artistName: String) : Artist.ArtistBiography
}