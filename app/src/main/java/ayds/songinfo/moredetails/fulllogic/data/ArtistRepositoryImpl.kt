package ayds.songinfo.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.domain.entity.Artist
import ayds.songinfo.moredetails.fulllogic.domain.repository.ArtistRepository

class ArtistRepositoryImpl (
    private val artistFromLocalStorage: ArtistLocalStorage,
    private val artistService : ArtistService
): ArtistRepository {

    override fun getArtist(artistName: String): Artist.ArtistBiography {
        val article = artistFromLocalStorage.getArticleFromDataBase(artistName)
        val artistBiography: Artist.ArtistBiography

        if(article != null ){
            artistBiography = article.markArticleAsLocal()
        }
        else {
            artistBiography = artistService.getFromService(artistName)
            if( artistBiography.biography.isNotEmpty()){
                artistFromLocalStorage.insertArtistIntoDataBase(artistBiography)
            }
        }
        return artistBiography
    }

    private fun Artist.ArtistBiography.markArticleAsLocal() = copy(biography = "[*] $biography")

}