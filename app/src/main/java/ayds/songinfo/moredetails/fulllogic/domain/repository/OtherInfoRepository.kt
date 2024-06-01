package ayds.songinfo.moredetails.fulllogic.domain.repository

import ayds.songinfo.moredetails.fulllogic.domain.entity.Card

interface OtherInfoRepository {
    fun getCard(artistName: String) : ArrayList<Card>
}