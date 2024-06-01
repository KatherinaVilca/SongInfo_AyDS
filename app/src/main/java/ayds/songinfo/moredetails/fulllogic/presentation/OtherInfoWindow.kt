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

    private lateinit var card1TextView: TextView
    private lateinit var card1buttonOpenArticle: Button
    private lateinit var card1Image: ImageView

    private lateinit var card2TextView: TextView
    private lateinit var card2buttonOpenArticle: Button
    private lateinit var card2Image: ImageView

    private lateinit var card3TextView: TextView
    private lateinit var card3buttonOpenArticle: Button
    private lateinit var card3Image: ImageView

    private lateinit var otherInfoPresenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initPropertiesView()
        initPresenter()
        observerPresenter()
        getCardsAsync()
    }

    private fun initPropertiesView(){
        card1TextView = findViewById(R.id.card1TextView)
        card1buttonOpenArticle = findViewById(R.id.card1buttonOpenArticle)
        card1Image = findViewById(R.id.card1Image)
    }

    private fun initPresenter(){
       OtherInfoInjector.init(this)
        otherInfoPresenter = OtherInfoInjector.presenter
    }

    private fun observerPresenter(){
        otherInfoPresenter.uiStateObservable.subscribe{
                cardsUiStates-> updateUi(cardsUiStates)
        }
    }

    private fun getCardsAsync() {
        Thread {
            getCards()
        }.start()
    }

    private fun getArtistName () = intent.getStringExtra(ARTIST_NAME_EXTRA)

    private fun getCards(){
        val artistName = getArtistName()
        otherInfoPresenter.getCardList(artistName!!)
    }

    private fun updateUi (listcardUiState: ArrayList<CardUiState>){
        runOnUiThread {
            updateButtonArticle(listcardUiState.first().infoUrl)
            updateLogoLastFM(listcardUiState.first().imageUrl)
            updateArticleText(listcardUiState.first().contentHtml)
        }
    }

    private fun updateButtonArticle(articleUrl: String){
        card1buttonOpenArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoLastFM(imageUrl: String){
        Picasso.get().load(imageUrl).into(   card1Image  )
    }

    private fun updateArticleText(articleHtml: String){
        card1TextView.text = Html.fromHtml(articleHtml)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}