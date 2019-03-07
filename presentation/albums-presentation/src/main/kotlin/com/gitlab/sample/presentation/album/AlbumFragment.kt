/***
 * Copyright 2018 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.gitlab.sample.presentation.album

import androidx.paging.PagedList
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitlab.sample.presentation.album.recycler.AlbumAdapter
import com.gitlab.sample.presentation.album.rmvvm.AlbumViewModel
import com.gitlab.sample.presentation.album.rmvvm.GetAlbumAction
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.navigator.Navigator
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import com.gitlab.sample.presentation.common.recycler.RecyclerState
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.albums.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumAdapter

    private lateinit var viewModel: AlbumViewModel

    override fun layoutId() = R.layout.albums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModel(viewModelFactory) {
            viewState().observe { state ->
                state.albums?.let { handleAlbums(it, state.totalCount) }
                state.loading.let(::handleLoading)
                state.error?.let { handleFailure(state.errorMessage, it) }
            }
            navigateViewState().observe(::handleNavigate)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        getAlbums()
    }

    private fun initView() {
        albumsView.layoutManager = LinearLayoutManager(context)
        albumsView.adapter = adapter
        adapter.onCreateViewHolder = { viewHolder ->
            // Pipe the View actions to the ViewModel
            viewHolder.actionStream.subscribe(viewModel.actionStream::onNext).track()
        }
    }

    private fun getAlbums() {
        viewModel.actionStream.onNext(GetAlbumAction(false))
    }

    private fun handleAlbums(albums: PagedList<RecyclerState>, totalCount: Int) {
        adapter.totalCount = totalCount
        adapter.submitList(albums)
        if (albums.isEmpty() && totalCount == 0) {
            emptyView.visible()
        } else {
            emptyView.gone()
        }
    }

    private fun handleFailure(@StringRes errorMessage: Int, @Suppress("UNUSED_PARAMETER") error: Throwable) {
        showFailure(errorMessage) { viewModel.actionStream.onNext(GetAlbumAction(true)) }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            progressView.visible()
        } else {
            progressView.gone()
        }
    }

    private fun handleNavigate(navigator: Navigator) {
        navigator.launchFragment(findNavController())
    }
}