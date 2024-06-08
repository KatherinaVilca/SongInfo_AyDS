package ayds.songinfo.moredetails.fulllogic.presentation


import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.fulllogic.domain.entity.Card
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository

interface OtherInfoPresenter {

    val uiStateObservable: Observable<ArrayList<CardUiState>>
    fun getCardList(artistName: String)
}
internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
) : OtherInfoPresenter{

    override val uiStateObservable = Subject<ArrayList<CardUiState>>()

     override fun getCardList(artistName: String) {
        val cards = repository.getCard(artistName)
        val cardsUiStates = toUiState(cards)

        uiStateObservable.notify(cardsUiStates)
    }

    private fun toUiState(cards: ArrayList<Card>): ArrayList<CardUiState>{
        val cardsUiStates = ArrayList<CardUiState>()

        for ( card in cards){
            if(card is Card.CardData) {
                cardsUiStates.add(
                    CardUiState(
                        artistName = card.artistName,
                        infoUrl = card.infoUrl,
                        contentHtml = cardDescriptionHelper.getContent(card),
                        imageUrl = card.sourceLogoUrl
                    )
                )
            }
        }
        return cardsUiStates
    }
}