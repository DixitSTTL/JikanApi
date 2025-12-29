package com.app.jikanapi.di

import com.app.jikanapi.data.repository.animList.AnimListRepositoryImpl
import com.app.jikanapi.data.repository.animList.dataSource.AnimListPagingSource
import com.app.jikanapi.data.repository.animdetail.AnimDetailRepositoryImpl
import com.app.jikanapi.domain.repository.AnimDetailRepository
import com.app.jikanapi.domain.repository.AnimListRepository
import org.koin.dsl.module

val RepositoryModule = module {

    single<AnimDetailRepository> { AnimDetailRepositoryImpl(get(), get()) }
    single<AnimListRepository> { AnimListRepositoryImpl(get(), get(), get()) }

    factory { AnimListPagingSource(get(), get()) }
}