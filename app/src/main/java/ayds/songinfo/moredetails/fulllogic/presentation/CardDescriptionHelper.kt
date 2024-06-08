package ayds.songinfo.moredetails.fulllogic.presentation

import ayds.songinfo.moredetails.fulllogic.domain.entity.Card
import java.util.Locale

interface CardDescriptionHelper{
    fun getContent(artistBiography: Card) : String
}

private const val HEADER = "<html><div width=400>"
private const val FONT = "<font face=\"arial\">"
private const val FINAL_HTML = "</font></div></html>"
internal class CardDescriptionHelperImpl() : CardDescriptionHelper {
    override fun getContent(card: Card): String {
        if( card is Card.CardData){
        val textBiography = getTextContent(card)
          return  textToHtml(textBiography, card.artistName)
        }
        return "No result"
    }

    private fun getTextContent(card: Card): String{
        val prefix = selectPrefix(card)
        var text = ""
        if(card is Card.CardData) {
            text = card.text.replace("\\n", "\n")
        }
        return "$prefix$text"
    }

    private fun selectPrefix(card: Card): String{
        var prefix =""
        if(card is Card.CardData && card.isLocallyStorage){
            prefix = "[*]"
        }
        return prefix
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