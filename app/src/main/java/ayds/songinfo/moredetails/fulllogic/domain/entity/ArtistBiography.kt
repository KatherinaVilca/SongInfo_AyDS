package ayds.songinfo.moredetails.fulllogic.domain.entity

    data class ArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String,
        var isLocallyStorage: Boolean = false,
    )

