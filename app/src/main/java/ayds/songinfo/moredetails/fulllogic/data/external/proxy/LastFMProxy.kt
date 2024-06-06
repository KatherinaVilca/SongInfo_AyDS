package ayds.songinfo.moredetails.fulllogic.data.external.proxy

import ayds.artist.external.lastFM.data.ArtistBiography
import ayds.artist.external.lastFM.data.LASTFM_LOGO_URL
import ayds.artist.external.lastFM.data.LastFMService
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

const val SOURCE_LASTFM = "lastFM"

class LastFMProxy(
    private val lastFMAPI : LastFMService
) : ProxyServiceCard {

    override fun getCard(artisName: String): Card {
        val artistBiography = lastFMAPI.getArtist(artisName)
        return artistBiography.toCard()
    }

    private fun ArtistBiography.toCard() =
        Card.CardData(
            artistName = artistName,
            text = biography,
            infoUrl = articleUrl,
            source = SOURCE_LASTFM,
            sourceLogoUrl = LASTFM_LOGO_URL,
        )
}