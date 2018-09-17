package com.gitlab.sample.presentation.album_details.di

import com.gitlab.sample.presentation.album_details.AlbumDetailsFragment
import com.gitlab.sample.presentation.common.di.BaseComponent
import dagger.Subcomponent

/**
 * Created by Hadi on 9/21/18.
 */
@AlbumDetailsScope
@Subcomponent(modules = [AlbumDetailsModule::class])
interface AlbumDetailsSubComponent : BaseComponent {
    fun inject(albumDetailsFragment: AlbumDetailsFragment)
}