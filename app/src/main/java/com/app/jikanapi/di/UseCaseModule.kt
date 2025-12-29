package com.app.jikanapi.di

import com.app.jikanapi.domain.usecase.UseCaseAnimDetailScreen
import com.app.jikanapi.domain.usecase.UseCaseMainScreen
import org.koin.dsl.module

val UseCaseModule = module {

    single { UseCaseMainScreen(get()) }
    single { UseCaseAnimDetailScreen(get()) }

}