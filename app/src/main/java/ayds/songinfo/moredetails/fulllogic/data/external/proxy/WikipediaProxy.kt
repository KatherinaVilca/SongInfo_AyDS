package ayds.songinfo.moredetails.fulllogic.data.external.proxy

import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.artist.external.wikipedia.data.WikipediaTrackService
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

private val WIKIPEDIA = "Wikipedia"
class WikipediaProxy(
    private val wikipediaService: WikipediaTrackService
) : ProxyServiceCard {

    override fun getCard(artistName: String): Card {
        val wikipediaArticle = wikipediaService.getInfo(artistName)
        return createCard(artistName, wikipediaArticle)
    }

    private fun createCard(artistName: String, wikipediaArticle: WikipediaArticle?) : Card {
        if (wikipediaArticle != null) {
            return Card.CardData(
                artistName = artistName,
                text = wikipediaArticle.description,
                infoUrl = wikipediaArticle.wikipediaURL,
                source = WIKIPEDIA,
                sourceLogoUrl = wikipediaArticle.wikipediaLogoURL
            )
        }
        return Card.EmptyCard(
                source = WIKIPEDIA,
            )
    }
}