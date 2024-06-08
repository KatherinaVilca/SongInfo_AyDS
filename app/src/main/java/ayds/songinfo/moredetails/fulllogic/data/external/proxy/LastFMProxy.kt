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
        return createCard(artistBiography)
    }

    private fun createCard(artistBiography: ArtistBiography) : Card {
        if(artistBiography.biography.isNotEmpty()) {
            return Card.CardData(
                artistName = artistBiography.artistName,
                text = artistBiography.biography,
                infoUrl = artistBiography.articleUrl,
                source = SOURCE_LASTFM,
                sourceLogoUrl = LASTFM_LOGO_URL,
            )
        }
        else {
            return Card.EmptyCard(
                source = SOURCE_LASTFM
            )
        }
    }
}