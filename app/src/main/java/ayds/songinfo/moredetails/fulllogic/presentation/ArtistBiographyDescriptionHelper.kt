package ayds.songinfo.moredetails.fulllogic.presentation

import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography
import java.util.Locale

interface ArtistBiographyDescriptionHelper{
    fun getBiography(artistBiography: ArtistBiography) : String
}

private const val HEADER = "<html><div width=400>"
private const val FONT = "<font face=\"arial\">"
private const val FINAL_HTML = "</font></div></html>"
internal class ArtistBiographyDescriptionHelperImpl() : ArtistBiographyDescriptionHelper {

    override fun getBiography(artistBiography: ArtistBiography): String {
        val textBiography = getTextBiography(artistBiography)
        return textToHtml( textBiography,artistBiography.artistName)
    }

    private fun getTextBiography(artistBiography: ArtistBiography): String{
        var prefix =""
        if(artistBiography.isLocallyStorage){
            prefix = "[*]"
        }
        val text = artistBiography.biography.replace("\\n", "\n")
        return "$prefix$text"
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append(HEADER)
        builder.append(FONT)
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append(FINAL_HTML)
        return builder.toString()
    }

}