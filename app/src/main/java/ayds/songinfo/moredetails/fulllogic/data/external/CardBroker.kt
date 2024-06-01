package ayds.songinfo.moredetails.fulllogic.data.external

import ayds.songinfo.moredetails.fulllogic.data.external.proxy.ProxyServiceCard
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

interface CardBroker{
    fun getCards(artistName: String): ArrayList<Card>
}

internal class CardBrokerImpl(
    private val proxyServiceA: ProxyServiceCard,
) : CardBroker {
    override fun getCards(artistName: String):ArrayList<Card> {
       val listCard = ArrayList<Card>()
        listCard.add(proxyServiceA.getCard(artistName))
        return listCard
    }
}