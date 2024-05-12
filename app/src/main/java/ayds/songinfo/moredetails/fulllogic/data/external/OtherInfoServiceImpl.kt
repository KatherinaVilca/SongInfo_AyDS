package ayds.songinfo.moredetails.fulllogic.data.external

import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import java.io.IOException
interface OtherInfoService {
    fun getArtist(artistName: String): ArtistBiography
}
internal class OtherInfoServiceImpl (
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver,
    private val lastFMAPI: LastFMAPI
) : OtherInfoService {
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