package ayds.songinfo.moredetails.fulllogic.domain.repository

import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography

interface OtherInfoRepository {
    fun getArtist(artistName: String) : ArtistBiography
}