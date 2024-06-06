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

        card2TextView = findViewById(R.id.card2TextView)
        card2buttonOpenArticle = findViewById(R.id.card2buttonOpenArticle)
        card2Image = findViewById(R.id.card2Image)

        card3TextView = findViewById(R.id.card3TextView)
        card3buttonOpenArticle = findViewById(R.id.card3buttonOpenArticle)
        card3Image = findViewById(R.id.card3Image)
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

    private fun updateUi(listcardUiState: ArrayList<CardUiState>) {
        runOnUiThread {
            for (i in 0 until listcardUiState.size) {
               when(i){
                   0 -> updateCar1(listcardUiState[0])
                   1 -> updateCar2(listcardUiState[1])
                   2 -> updateCar3(listcardUiState[2])
               }
            }
        }
    }

    private fun updateCar1(cardUiState: CardUiState){
        updateButtonArticleCard1(cardUiState.infoUrl)
        updateLogoCard1(cardUiState.imageUrl)
        updateArticleTextCard1(cardUiState.contentHtml)
    }
    private fun updateCar2(cardUiState: CardUiState){
        updateButtonArticleCard2(cardUiState.infoUrl)
        updateLogoCard2(cardUiState.imageUrl)
        updateArticleTextCard2(cardUiState.contentHtml)
    }
    private fun updateCar3(cardUiState: CardUiState){
        updateButtonArticleCard3(cardUiState.infoUrl)
        updateLogoCard3(cardUiState.imageUrl)
        updateArticleTextCard3(cardUiState.contentHtml)
    }

    private fun updateButtonArticleCard1(articleUrl: String){
        card1buttonOpenArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoCard1(imageUrl: String){
        Picasso.get().load(imageUrl).into(   card1Image  )
    }

    private fun updateArticleTextCard1(articleHtml: String){
        card1TextView.text = Html.fromHtml(articleHtml)
    }

    private fun updateButtonArticleCard2(articleUrl: String){
        card2buttonOpenArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoCard2(imageUrl: String){
        Picasso.get().load(imageUrl).into(   card2Image  )
    }

    private fun updateArticleTextCard2(articleHtml: String){
        card2TextView.text = Html.fromHtml(articleHtml)
    }

    private fun updateButtonArticleCard3(articleUrl: String){
        card3buttonOpenArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(articleUrl))
            startActivity(intent)
        }
    }

    private fun updateLogoCard3(imageUrl: String){
        Picasso.get().load(imageUrl).into(   card3Image  )
    }

    private fun updateArticleTextCard3(articleHtml: String){
        card3TextView.text = Html.fromHtml(articleHtml)
    }
    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}