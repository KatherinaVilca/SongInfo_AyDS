package ayds.songinfo.moredetails.fulllogic.presentation


import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository

interface OtherInfoPresenter {

    val uiStateObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)
}
internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper
) : OtherInfoPresenter{

    override val uiStateObservable = Subject<ArtistBiographyUiState>()
    override fun getArtistInfo(artistName: String) {
        val artistBiography = repository.getArtist(artistName)
        val uiState = artistBiography.toUiState()

        uiStateObservable.notify(uiState)
    }

    private fun ArtistBiography.toUiState() = ArtistBiographyUiState(
        artistName = artistName,
        articleUrl = articleUrl,
        articleHtml = artistBiographyDescriptionHelper.getBiography(this)
        )
}