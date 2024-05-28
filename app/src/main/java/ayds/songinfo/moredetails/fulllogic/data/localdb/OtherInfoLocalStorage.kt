package ayds.songinfo.moredetails.fulllogic.data.localdb


import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

interface OtherInfoLocalStorage {
    fun getCard(artistName: String): Card?
    fun insertCard(artistBiography: Card, artistName: String)
}

internal class OtherInfoLocalStorageImpl (
    private var cardDataBase: CardDatabase
): OtherInfoLocalStorage {

    override fun getCard(artistName: String): Card? {
        val cardEntity = cardDataBase.CardDao().getArticleByArtistName(artistName)
        return cardEntity?.let{
            Card(
                cardEntity.artistName,
                cardEntity.text,
                cardEntity.url,
                cardEntity.source,
                "")}
    }

    override fun insertCard(card: Card, artistName: String) {
       cardDataBase.CardDao().insertArticle(
            CardEntity(
                card.artistName,
                card.text,
                card.infoUrl,
                card.source
            ))
    }
}