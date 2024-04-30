package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

private const val LASTFM_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val LASTFM_URL = "https://ws.audioscrobbler.com/2.0/"
private const val ARTICLE_DATABASE_NAME = "database-name-thename"
data class ArtistBiography(val artistName: String, val biography: String, val articleUrl: String)
class OtherInfoWindow : Activity() {

    private lateinit var articleTextPane: TextView
    private lateinit var openButtonArticle : Button
    private lateinit var lastFMImage : ImageView
    private lateinit var articleDataBase: ArticleDatabase
    private lateinit var retrofit : Retrofit
    private lateinit var lastFMAPI: LastFMAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initDataBase()
        initLastFM()
        getArtistInfoAsync()
    }

    private fun initProperties(){
        articleTextPane = findViewById(R.id.articleTextPane)
        openButtonArticle = findViewById(R.id.openButtonArticle)
        lastFMImage = findViewById(R.id.lastFMImage)
    }
    private fun initRetrofit(){
       retrofit = Retrofit.Builder()
            .baseUrl( LASTFM_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun initLastFM(){
        initRetrofit()
        lastFMAPI = retrofit.create(LastFMAPI::class.java)
    }

    private fun initDataBase(){
       articleDataBase =
            databaseBuilder(this, ArticleDatabase::class.java, ARTICLE_DATABASE_NAME ).build()
    }
    private fun getArtistInfoAsync() {
        val artist = getArtistName()
        Thread {
            if (artist != null) {
                getArtistInfo(artist)
            }
        }.start()
    }

    private fun getArtistName () = intent.getStringExtra(ARTIST_NAME_EXTRA)

    private fun getArtistInfo(artistName: String){
        val article = getArtistFromRepository(artistName)
        updateUi(article)
    }

    private fun getArtistFromRepository(artistName: String): ArtistBiography{
       val article = getArticleFromDataBase(artistName)
       val artistBiography: ArtistBiography

        if(article != null ){
            artistBiography = article.markArticleAsLocal()
        }
        else {
            artistBiography = getFromService(artistName)
            if( artistBiography.biography.isNotEmpty()){
                insertArtistIntoDataBase(artistBiography)
            }
        }
      return artistBiography
    }

    private fun ArtistBiography.markArticleAsLocal() = copy(biography = "[*] $biography")

    private fun getArticleFromDataBase(artistName: String): ArtistBiography?{
        val artistEntity = articleDataBase.ArticleDao().getArticleByArtistName(artistName)

        return artistEntity?.let{ ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl )}
    }

    private fun getFromService(artistName: String): ArtistBiography{

        var artistBiography = ArtistBiography(artistName,"", "")
        try {
            val callResponse = getArtistInfoFromService(artistName)
            artistBiography = getArtistBioFromCallResponse (callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return artistBiography
    }

    private fun getArtistBioFromCallResponse(callResponse: String?, artistName: String): ArtistBiography{
        val gson = Gson()
        val jobj = gson.fromJson(callResponse, JsonObject::class.java)
        val artist = jobj["artist"].getAsJsonObject()
        val bio = artist["bio"].getAsJsonObject()
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString?.replace("\\n", "\n") ?: "No Results"
        return ArtistBiography(artistName, text, url.asString)
    }
    private fun getArtistInfoFromService(artistName: String) = lastFMAPI.getArtistInfo(artistName).execute()

    private fun insertArtistIntoDataBase(artistBiography: ArtistBiography){
        articleDataBase.ArticleDao().insertArticle(ArticleEntity(artistBiography.artistName, artistBiography.biography,artistBiography.articleUrl))
    }

    private fun updateUi (artistBiography: ArtistBiography){
        runOnUiThread {
            updateButtonArticle(artistBiography.articleUrl)
            updateLogoLastFM()
            updateArticleText(artistBiography)
        }
    }

    private fun updateButtonArticle(articleUrl: String){
        openButtonArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoLastFM(){
        Picasso.get().load(LASTFM_IMAGE_URL).into(   lastFMImage  )
    }

    private fun updateArticleText(artistBiography: ArtistBiography){
        val text = artistBiography.biography.replace("\\n", "\n")
        articleTextPane.text = Html.fromHtml(textToHtml(text, artistBiography.artistName))
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }


    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
