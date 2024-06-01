package ayds.songinfo.moredetails.fulllogic.data.external.proxy

import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

interface ProxyServiceCard {
    fun getCard(artisName: String) : Card
}