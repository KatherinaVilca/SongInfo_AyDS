package ayds.songinfo.moredetails.fulllogic.data.localdb


import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

interface OtherInfoLocalStorage {
    fun getCard(artistName: String): ArrayList<Card>?
    fun insertCard(listCard: ArrayList<Card>, artistName: String)
}

internal class OtherInfoLocalStorageImpl (
    private var cardDataBase: CardDatabase
): OtherInfoLocalStorage {

    override fun getCard(artistName: String): ArrayList<Card>? {
        val cardsEntities = cardDataBase.CardDao().getCardByArtistName(artistName)
        val cards = ArrayList<Card> ()

        if (cardsEntities != null) {
            for (cardEntity in cardsEntities) {
                cards.add(
                    Card(
                        cardEntity.artistName,
                        cardEntity.text,
                        cardEntity.url,
                        cardEntity.source,
                        "")
                )}
        }
        return cards
    }

    override fun insertCard(listCard: ArrayList<Card>, artistName: String) {
        for (card in listCard) {
            cardDataBase.CardDao().insertCard(
                CardEntity(
                    card.artistName,
                    card.text,
                    card.infoUrl,
                    card.source
                )
            )
        }
    }
}