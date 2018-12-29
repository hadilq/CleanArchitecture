/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitlab.sample.presentation.album_details.di.viewholder

import com.gitlab.sample.presentation.album.di.AlbumsScope
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolder
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolderFactory
import com.gitlab.sample.presentation.album.recycler.AlbumViewHolder
import com.gitlab.sample.presentation.album.recycler.LoadingViewHolder
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AlbumsViewHolderModule {

    @Binds
    @AlbumsScope
    internal abstract fun bindViewHolderFactory(factory: AlbumsViewHolderFactoryImpl): AlbumsViewHolderFactory

    @Binds
    @IntoMap
    @AlbumsViewHolderKey(AlbumViewHolder::class)
    internal abstract fun bindsAlbumViewHolder(viewHolder: AlbumViewHolder): AlbumsViewHolder

    @Binds
    @IntoMap
    @AlbumsViewHolderKey(LoadingViewHolder::class)
    internal abstract fun bindsLoadingViewHolder(viewHolder: LoadingViewHolder): AlbumsViewHolder
}