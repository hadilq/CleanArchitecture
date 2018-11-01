package com.gitlab.sample.presentation.album_details.di.viewholder

import com.gitlab.sample.presentation.album_details.di.AlbumDetailsScope
import javax.inject.Inject
import javax.inject.Provider

@AlbumDetailsScope
@Suppress("UNCHECKED_CAST")
class AlbumViewHolderFactoryImpl
@Inject constructor(
        private val creators: Map<Class<out AlbumViewHolder>, @JvmSuppressWildcards Provider<AlbumViewHolder>>
) : AlbumViewHolderFactory {

    override fun <T : AlbumViewHolder> create(clazz: Class<T>): T {
        val creator =
                creators[clazz] ?: creators.asIterable().firstOrNull { clazz.isAssignableFrom(it.key) }?.value
                ?: throw IllegalArgumentException("Unknown AlbumViewHolder class $clazz")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}