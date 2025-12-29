package com.app.jikanapi.di

import com.app.jikanapi.domain.room.DatabaseHelper
import org.koin.dsl.module

val AppModule = module {
    includes(
        NetworkModule,
        ViewModelModule,
        UseCaseModule,
        RepositoryModule
    )

    single { DatabaseHelper.getInstance(get()) }

}