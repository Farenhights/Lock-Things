package br.com.eas.lockthings.di

import br.com.eas.lockthings.presentation.viewmodel.LockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LockViewModel() }
}