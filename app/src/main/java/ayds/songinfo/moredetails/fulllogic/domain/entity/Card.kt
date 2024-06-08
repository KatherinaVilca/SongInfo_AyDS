package ayds.songinfo.moredetails.fulllogic.domain.entity

private const val NO_RESULT = "No result"
sealed class Card{
     data class CardData(
          val artistName: String,
          val text: String,
          val infoUrl: String,
          val source: String,
          val sourceLogoUrl: String,
          var isLocallyStorage: Boolean = false,
     ) : Card()
     data class EmptyCard(
          val artistName: String = NO_RESULT,
          val text: String = NO_RESULT,
          val infoUrl: String = NO_RESULT,
          val source: String,
          val sourceLogoUrl: String = NO_RESULT,
          var isLocallyStorage: Boolean = false,

          ) : Card()
}

