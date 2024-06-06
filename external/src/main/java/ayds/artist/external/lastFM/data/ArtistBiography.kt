package ayds.artist.external.lastFM.data

const val LASTFM_LOGO_URL =  "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

data class ArtistBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
)
