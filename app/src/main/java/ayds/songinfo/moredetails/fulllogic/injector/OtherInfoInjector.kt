package ayds.songinfo.moredetails.fulllogic.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastFM.injector.LastFMInjector
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.external.CardBroker
import ayds.songinfo.moredetails.fulllogic.data.external.CardBrokerImpl
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.LastFMProxy
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.ProxyServiceCard
import ayds.songinfo.moredetails.fulllogic.data.localdb.CardDatabase
import ayds.songinfo.moredetails.fulllogic.data.localdb.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.CardDescriptionHelperImpl
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
       ///Broker
       val lastFMService = LastFMInjector.lastFMService
       val lastFMProxy : ProxyServiceCard = LastFMProxy(lastFMService)

       val cardBroker : CardBroker = CardBrokerImpl(lastFMProxy)

       val localStorage = OtherInfoLocalStorageImpl(articleDataBase)
       val repository = OtherInfoRepositoryImpl(localStorage, cardBroker)

       //presentation
       val cardDescriptionHelper= CardDescriptionHelperImpl()
       presenter = OtherInfoPresenterImpl(repository, cardDescriptionHelper)
   }
}