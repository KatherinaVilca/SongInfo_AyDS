package ayds.songinfo.moredetails.fulllogic.data.external

import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import com.google.gson.Gson
import com.google.gson.JsonObject

interface  LastFMToArtistBiographyResolver{
     fun map(callResponse: String?, artistName: String) : ArtistBiography
}

private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val NO_RESULT ="No Results"
internal class LastFMToArtistBiographyResolverImpl : LastFMToArtistBiographyResolver{
   override fun map (callResponse: String?, artistName: String): ArtistBiography {
        val gson = Gson()
        val jobj = gson.fromJson(callResponse, JsonObject::class.java)
        val artist = jobj[ARTIST].getAsJsonObject()
        val bio = artist[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        val url = artist[URL]
        val text = extract?.asString?.replace("\\n", "\n") ?: NO_RESULT
        return ArtistBiography(artistName, text, url.asString)
    }

}