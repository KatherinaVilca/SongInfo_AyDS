package ayds.songinfo.moredetails.fulllogic.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastFM.injector.LastFMInjector
import ayds.artist.external.newyorktimes.injector.NYTimesInjector
import ayds.artist.external.wikipedia.injector.WikipediaInjector
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.external.CardBroker
import ayds.songinfo.moredetails.fulllogic.data.external.CardBrokerImpl
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.LastFMProxy
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.NYTimesProxy
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.ProxyServiceCard
import ayds.songinfo.moredetails.fulllogic.data.external.proxy.WikipediaProxy
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
       val services = ArrayList<ProxyServiceCard> ()
       val lastFMProxy : ProxyServiceCard = LastFMProxy(LastFMInjector.lastFMService)
       val nyTimesProxy = NYTimesProxy(NYTimesInjector.nyTimesService)
       val wikipediaProxy = WikipediaProxy(WikipediaInjector.wikipediaTrackService)

       services.add(lastFMProxy)
       services.add(nyTimesProxy)
       services.add(wikipediaProxy)

       val cardBroker : CardBroker = CardBrokerImpl(services)

       val localStorage = OtherInfoLocalStorageImpl(articleDataBase)
       val repository = OtherInfoRepositoryImpl(localStorage, cardBroker)

       //presentation
       val cardDescriptionHelper= CardDescriptionHelperImpl()
       presenter = OtherInfoPresenterImpl(repository, cardDescriptionHelper)
   }
}