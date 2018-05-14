package es.florit.crypt.dagger

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import es.florit.crypt.BuildConfig
import es.florit.crypt.datasource.api.service.CryptService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

/**
 * Provides all the required stuff for retrofit2
 *
 * Created by ismael on 19/3/18.
 */
@Module
class RetrofitModule(internal var mBaseUrl: String = "https://test.cryptojet.io/") {

    companion object {
        val BASE_IMG: String = "https://test.cryptojet.io/"
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient().newBuilder()
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original = chain.request()
                        val originalHttpUrl = original.url()

                        val url = originalHttpUrl.newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build()

                        // Request customization: add request headers
                        val requestBuilder = original.newBuilder()
                                .url(url)

                        val request = requestBuilder.build()
                        return chain.proceed(request)
                    }
                })
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        return client.build()
    }

    @Provides
    @Singleton
    fun provideAPI(): CryptService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .baseUrl(mBaseUrl)
                .client(provideOkHttpClient())
                .build().create(CryptService::class.java)
    }
}