package com.example.composeapp.notes.di

import android.app.Application
import androidx.room.Room
import com.example.composeapp.BuildConfig
import com.example.composeapp.helper.AppConst.BASE_URL
import com.example.composeapp.helper.AppConst.OPENAI_API_KEY
import com.example.composeapp.notes.data.local_db.NoteDatabase
import com.example.composeapp.notes.data.local_db.NoteDatabase.Companion.DATABASE_NAME
import com.example.composeapp.notes.data.repository.NoteRepository
import com.example.composeapp.notes.data.repository.NoteRepositoryImpl
import com.example.composeapp.notes.data.retrofit.NotesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.notesDao)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val client = OkHttpClient.Builder()

        client.addInterceptor { chain ->
            var request: Request = chain.request()
            request = request.newBuilder()
                .header("Authorization", "Bearer $OPENAI_API_KEY")
                .build()
            chain.proceed(request)
        }

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        client.addInterceptor(interceptor)

        return client.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMyApiService(retrofit: Retrofit): NotesAPI {
        return retrofit.create(NotesAPI::class.java)
    }
}