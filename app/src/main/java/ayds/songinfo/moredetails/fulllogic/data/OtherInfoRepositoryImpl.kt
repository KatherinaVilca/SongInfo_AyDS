package ayds.songinfo.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.data.external.OtherInfoService
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.domain.repository.OtherInfoRepository

class OtherInfoRepositoryImpl (
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val otherInfoService: OtherInfoService
): OtherInfoRepository {

    override fun getArtist(artistName: String): ArtistBiography {
        val article = otherInfoLocalStorage.getArticle(artistName)
        val artistBiography: ArtistBiography

        if(article != null ){
            markArticleAsLocal(article)
            artistBiography = article
        }
        else {
            artistBiography = otherInfoService.getArtist(artistName)
            if( artistBiography.biography.isNotEmpty()){
                otherInfoLocalStorage.insertArtist(artistBiography)
            }
        }
        return artistBiography
    }

    private fun markArticleAsLocal(article :ArtistBiography) {
        article.isLocallyStorage=true
    }

}