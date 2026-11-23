package ke.co.hakicheck.data.remote

import ke.co.hakicheck.data.remote.model.VerificationRequestDto
import ke.co.hakicheck.data.remote.model.VerificationResponseDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface HakiCheckApiService {

    @POST("api/v1/verify")
    suspend fun verifyClaim(
        @Body request: VerificationRequestDto
    ): Response<VerificationResponseDto>

    @GET("api/v1/trending")
    suspend fun getTrendingClaims(): Response<List<VerificationResponseDto>>
}

object ApiClient {
    private const val DEFAULT_BASE_URL = "http://10.0.2.2:8000/" // Android emulator localhost alias

    fun createService(baseUrl: String = DEFAULT_BASE_URL): HakiCheckApiService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HakiCheckApiService::class.java)
    }
}
