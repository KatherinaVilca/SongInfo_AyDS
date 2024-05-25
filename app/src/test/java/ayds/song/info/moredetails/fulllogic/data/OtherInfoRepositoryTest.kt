package ayds.song.info.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.external.OtherInfoService
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OtherInfoRepositoryTest{
    private val localStorage: OtherInfoLocalStorage = mockk(relaxUnitFun = true)
    private val service: OtherInfoService = mockk(relaxUnitFun = true)

    private val otherInfoRepository : OtherInfoRepository = OtherInfoRepositoryImpl(
        localStorage,service
    )

    @Test
    fun `given existing article by artistName should return artistBiography and markArticleAsLocal`(){
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl", false)

        every {localStorage.getArticle("artistName")} returns artistBiography

        val result =otherInfoRepository.getArtist("artistName")

        assertEquals(artistBiography, result)
        assertTrue(result.isLocallyStorage)
    }

    @Test
    fun `given non existing article by artistName in localStorage should search on Service, store it and return artistBiography`(){
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl", false)
        every{localStorage.getArticle("artistName")} returns null
        every{service.getArtist("artistName")} returns artistBiography
        //porque? every{localStorage.insertArtist(artistBiography)}

        val result = otherInfoRepository.getArtist("artistName")

        assertEquals(artistBiography,result)
        assertFalse(result.isLocallyStorage)
        verify { localStorage.insertArtist(artistBiography) }
    }

    @Test
    fun `given non existing article by artistName in localStorage should seach on service and not store it and return artistBiography`(){
        val artistBiography = ArtistBiography("artistName", "biography", "articleUrl", false)
        every{localStorage.getArticle("artistName")} returns null
        every{service.getArtist("artistName")} returns artistBiography

        val result = otherInfoRepository.getArtist("artistName")

        assertEquals(artistBiography,result)
        assertFalse(result.isLocallyStorage)
        // porque? verify(inverse = true) { otherInfoLocalStorage.insertArtist(artistBiography) }
    }
}