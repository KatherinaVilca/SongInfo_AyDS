package ayds.songinfo.moredetails.fulllogic.injector

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.external.LastFMAPI
import ayds.songinfo.moredetails.fulllogic.data.external.LastFMToArtistBiographyResolver
import ayds.songinfo.moredetails.fulllogic.data.external.LastFMToArtistBiographyResolverImpl
import ayds.songinfo.moredetails.fulllogic.data.external.OtherInfoServiceImpl
import ayds.songinfo.moredetails.fulllogic.data.localdb.ArticleDatabase
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_URL = "https://ws.audioscrobbler.com/2.0/"
private const val ARTICLE_DATABASE_NAME = "database-name-thename"
object OtherInfoInjector {

   lateinit var presenter : OtherInfoPresenter
    fun init(context : Context){

        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)
        val articleDataBase = Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_DATABASE_NAME).build()
        val lastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()

        // data
        val service = OtherInfoServiceImpl(lastFMToArtistBiographyResolver,lastFMAPI)
        val localStorage = OtherInfoLocalStorageImpl(articleDataBase)
        val repository = OtherInfoRepositoryImpl(localStorage,service)

        //presentation
        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()
        //porque lateinit?
        presenter = OtherInfoPresenterImpl(repository,artistBiographyDescriptionHelper)
    }
}