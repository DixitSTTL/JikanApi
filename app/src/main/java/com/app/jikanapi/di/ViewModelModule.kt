package com.app.jikanapi.di

import com.app.jikanapi.presentation.animdetail.AnimDetailScreenViewModel
import com.app.jikanapi.presentation.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {

    viewModel { MainScreenViewModel(get()) }
    viewModel { (anim: String) -> AnimDetailScreenViewModel(get(), anim) }

}