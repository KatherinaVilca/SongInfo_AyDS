package ayds.songinfo.moredetails.fulllogic.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastFM.injector.LastFMInjector
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.localdb.CardDatabase
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl


private const val ARTICLE_DATABASE_NAME = "database-name-thename"
object OtherInfoInjector {

   lateinit var presenter : OtherInfoPresenter
   fun init(context : Context){

       LastFMInjector.init()

       val articleDataBase = Room.databaseBuilder(context,
           CardDatabase::class.java, ARTICLE_DATABASE_NAME).build()

        // data
       val localStorage = OtherInfoLocalStorageImpl(articleDataBase)
       val repository = OtherInfoRepositoryImpl(localStorage, LastFMInjector.lastFMService)

       //presentation
       val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()
       presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
   }
}