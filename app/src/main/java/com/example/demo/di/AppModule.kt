package com.example.demo.di

import com.example.demo.ui.theme.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}