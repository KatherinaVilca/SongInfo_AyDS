package ayds.songinfo.moredetails.fulllogic.domain.entity

sealed class Artist {
    data class ArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String
    ) : Artist()

}