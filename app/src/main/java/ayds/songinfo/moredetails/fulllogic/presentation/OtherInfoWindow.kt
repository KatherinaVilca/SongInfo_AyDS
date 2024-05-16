package ayds.songinfo.moredetails.fulllogic.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import ayds.songinfo.moredetails.fulllogic.injector.OtherInfoInjector
import com.squareup.picasso.Picasso

class OtherInfoWindow : Activity() {

    private lateinit var articleTextPane: TextView
    private lateinit var openButtonArticle : Button
    private lateinit var lastFMImage : ImageView

    private lateinit var otherInfoPresenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initPropertiesView()
        initPresenter()
        observerPresenter()
        getArtistInfoAsync()
    }

    private fun initPropertiesView(){
        articleTextPane = findViewById(R.id.articleTextPane)
        openButtonArticle = findViewById(R.id.openButtonArticle)
        lastFMImage = findViewById(R.id.lastFMImage)
    }

    private fun initPresenter(){
       OtherInfoInjector.init(this)
        otherInfoPresenter = OtherInfoInjector.presenter
    }

    private fun observerPresenter(){
        otherInfoPresenter.uiStateObservable.subscribe{
            artistBiography-> updateUi(artistBiography)
        }
    }

    private fun getArtistInfoAsync() {
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistName () = intent.getStringExtra(ARTIST_NAME_EXTRA)

    private fun getArtistInfo(){
        val artistName = getArtistName()
        otherInfoPresenter.getArtistInfo(artistName!!)
    }

    private fun updateUi (artistBiography: ArtistBiographyUiState){
        runOnUiThread {
            updateButtonArticle(artistBiography.articleUrl)
            updateLogoLastFM(artistBiography.imageUrl)
            updateArticleText(artistBiography.articleHtml)
        }
    }

    private fun updateButtonArticle(articleUrl: String){
        openButtonArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoLastFM(imageUrl: String){
        Picasso.get().load(imageUrl).into(   lastFMImage  )
    }

    private fun updateArticleText(articleHtml: String){
        articleTextPane.text = Html.fromHtml(articleHtml)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}