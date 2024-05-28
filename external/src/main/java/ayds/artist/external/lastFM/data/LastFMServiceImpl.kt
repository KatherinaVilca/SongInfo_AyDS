package ayds.artist.external.lastFM.data

import java.io.IOException
interface LastFMService {
    fun getArtist(artistName: String): ArtistBiography
}
internal class LastFMServiceImpl (
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver,
    private val lastFMAPI: LastFMAPI
) : LastFMService {
    override fun getArtist(artistName: String): ArtistBiography{

        var artistBiography = ArtistBiography(artistName,"", "")
        try {
            val callResponse = getArtistInfoFromService(artistName)
            artistBiography = lastFMToArtistBiographyResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return artistBiography
    }
    private fun getArtistInfoFromService(artistName: String) = lastFMAPI.getArtistInfo(artistName).execute()
}