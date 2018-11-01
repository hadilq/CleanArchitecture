package com.gitlab.sample.presentation.album_details.di.viewholder

import com.gitlab.sample.presentation.album.di.AlbumsScope
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolder
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolderFactory
import javax.inject.Inject
import javax.inject.Provider

@AlbumsScope
@Suppress("UNCHECKED_CAST")
class AlbumsViewHolderFactoryImpl
@Inject constructor(
        private val creators: Map<Class<out AlbumsViewHolder>, @JvmSuppressWildcards Provider<AlbumsViewHolder>>
) : AlbumsViewHolderFactory {

    override fun <T : AlbumsViewHolder> create(clazz: Class<T>): T {
        val creator =
                creators[clazz] ?: creators.asIterable().firstOrNull { clazz.isAssignableFrom(it.key) }?.value
                ?: throw IllegalArgumentException("Unknown AlbumsViewHolder class $clazz")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}