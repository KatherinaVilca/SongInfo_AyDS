package ayds.songinfo.moredetails.fulllogic.data.localdb


import ayds.songinfo.moredetails.fulllogic.domain.entity.ArtistBiography

interface OtherInfoLocalStorage {
    fun getArticle(artisName: String): ArtistBiography?
    fun insertArtist(artistBiography: ArtistBiography)
}

internal class OtherInfoLocalStorageImpl (
    private var articleDataBase: ArticleDatabase
): OtherInfoLocalStorage {

    override fun getArticle(artistName: String): ArtistBiography? {
        val artistEntity = articleDataBase.ArticleDao().getArticleByArtistName(artistName)
        return artistEntity?.let{
            ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl )}
    }

    override fun insertArtist(artistBiography: ArtistBiography) {
            articleDataBase.ArticleDao().insertArticle(
                ArticleEntity(
                    artistBiography.artistName, artistBiography.biography,artistBiography.articleUrl))
    }
}