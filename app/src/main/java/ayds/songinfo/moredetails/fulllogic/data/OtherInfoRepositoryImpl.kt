package ayds.songinfo.moredetails.fulllogic.data

import ayds.artist.external.lastFM.data.ArtistBiography
import ayds.artist.external.lastFM.data.LastFMService
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository

class OtherInfoRepositoryImpl (
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val lastFMService: LastFMService
): OtherInfoRepository {

    override fun getCard(artistName: String): Card {
        val article = otherInfoLocalStorage.getCard(artistName)
        val card: Card

        if(article != null ){
            markArticleAsLocal(article)
            card = article
        }
        else {
            card = lastFMService.getArtist(artistName).toCard()
            if( card.text.isNotEmpty()){
                otherInfoLocalStorage.insertCard(card,artistName)
            }
        }
        return card
    }

    private fun markArticleAsLocal(article: Card) {
        article.isLocallyStorage = true
    }

    private fun ArtistBiography.toCard() =
        Card(
            artistName = artistName,
            text = biography,
            infoUrl = articleUrl,
            source = "",
            sourceLogoUrl = ""
        )

}