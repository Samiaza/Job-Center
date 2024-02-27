package com.example.app.di.components

import com.example.app.di.modules.NetworkModule
import com.example.app.ui.activities.CompanyDetailsActivity
import com.example.app.ui.activities.MainActivity
import com.example.app.ui.activities.ResumeActivity
import com.example.app.ui.activities.VacancyDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: CompanyDetailsActivity)
    fun inject(activity: ResumeActivity)
    fun inject(activity: VacancyDetailsActivity)
}