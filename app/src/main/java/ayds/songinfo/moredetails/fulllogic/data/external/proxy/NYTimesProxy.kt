package ayds.songinfo.moredetails.fulllogic.data.external.proxy

import ayds.artist.external.newyorktimes.data.NYT_LOGO_URL
import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

private const val NYTIMES = "NewyorkTimes"
class NYTimesProxy(
    private val nyTimes: NYTimesService
): ProxyServiceCard{
    override fun getCard(artisName: String): Card {
        val nyTimesArticle = nyTimes.getArtistInfo(artisName)
        return createCard(nyTimesArticle)
    }

    private fun createCard(nyTimesArticle : NYTimesArticle): Card {
        if (nyTimesArticle is NYTimesArticle.NYTimesArticleWithData) {
            return Card.CardData(
                artistName = nyTimesArticle.name!!,
                text = nyTimesArticle.info!!,
                infoUrl = nyTimesArticle.url,
                source = NYTIMES,
                sourceLogoUrl = NYT_LOGO_URL,
            )
        }
        return Card.EmptyCard(
                source = NYTIMES,
                sourceLogoUrl = NYT_LOGO_URL,
                )
    }

}