package ayds.artist.external.lastFM.injector

import ayds.artist.external.lastFM.data.LastFMService
import ayds.artist.external.lastFM.data.LastFMServiceImpl
import ayds.artist.external.lastFM.data.LastFMToArtistBiographyResolverImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {

    lateinit var lastFMService : LastFMService

    fun init(){
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(ayds.artist.external.lastFM.data.LastFMAPI::class.java)
        val lastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()

        lastFMService = LastFMServiceImpl(lastFMToArtistBiographyResolver, lastFMAPI)
    }
}