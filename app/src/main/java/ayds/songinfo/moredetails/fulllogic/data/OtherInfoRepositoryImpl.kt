package ayds.songinfo.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.data.external.CardBroker
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository

class OtherInfoRepositoryImpl (
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val cardBroker: CardBroker
): OtherInfoRepository {

    override fun getCard(artistName: String): ArrayList<Card> {
        var listCard : ArrayList<Card> = otherInfoLocalStorage.getCard(artistName)!!


        if (listCard.isNotEmpty()) {
            markCardAsLocal(listCard)
        }
            else {
                listCard = cardBroker.getCards(artistName)
                if( listCard.isNotEmpty()){
                    otherInfoLocalStorage.insertCard(listCard,artistName)
                }
            }
        return listCard
    }

    private fun markCardAsLocal(cards: ArrayList<Card>) {
        for (card in cards) {
            if( card is Card.CardData) {
                card.isLocallyStorage = true
            }
        }
    }

}