package com.gitlab.sample.presentation.album_details.di

import com.gitlab.sample.presentation.common.di.Injector

/**
 * Created by Hadi on 9/21/18.
 */
interface AlbumDetailsInjector : Injector {
    fun createComponent(): AlbumDetailsSubComponent

    companion object {
        var injector: AlbumDetailsInjector? = null

        fun createAlbumsComponent(): AlbumDetailsSubComponent {
            return injector!!.createComponent()
        }
    }
}