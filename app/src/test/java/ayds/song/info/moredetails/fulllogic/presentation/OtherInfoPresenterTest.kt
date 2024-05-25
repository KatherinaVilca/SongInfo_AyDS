package ayds.song.info.moredetails.fulllogic.presentation

import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyUiState
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {
    private val repository : OtherInfoRepository = mockk(relaxUnitFun = true)
    private val descriptionHelper : ArtistBiographyDescriptionHelper = mockk(relaxUnitFun = true)

    private val presenter : OtherInfoPresenter = OtherInfoPresenterImpl(
        repository,descriptionHelper
    )

    @Test
    fun `should return artistBiographyUiState instead of artistBiography`(){
        val artistBiography = ArtistBiography(
            "artistName",
            "biography",
            "articleUrl",
            true
        )

        every{repository.getArtist("artistName")} returns artistBiography
        every{descriptionHelper.getBiography(artistBiography)} returns "articleHtlm"

        //val result = presenter.getArtistInfo("artistName")

        val artistBiographyTester: (ArtistBiographyUiState) -> Unit = mockk(relaxed = true)

        presenter.uiStateObservable.subscribe(artistBiographyTester)
        presenter.getArtistInfo("artistName")

        val result = ArtistBiographyUiState("artistName", "articleUrl", "articleHtml")
        verify { artistBiographyTester(result) }
    }
}