package ayds.songinfo.moredetails.fulllogic.domain.entity
data class Card(
     val artistName: String,
     val text: String,
     val infoUrl: String,
     val source: String,
     val sourceLogoUrl: String,
     var isLocallyStorage: Boolean = false,
    )

