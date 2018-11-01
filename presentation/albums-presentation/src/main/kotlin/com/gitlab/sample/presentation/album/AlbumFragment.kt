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

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gitlab.sample.presentation.album.recycler.AlbumAdapter
import com.gitlab.sample.presentation.album.recycler.AlbumViewData
import com.gitlab.sample.presentation.album.rmvvm.AlbumViewModel
import com.gitlab.sample.presentation.album.rmvvm.ErrorAlbumViewState
import com.gitlab.sample.presentation.album.rmvvm.GetAlbumAction
import com.gitlab.sample.presentation.album.rmvvm.GetAlbumViewState
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.Navigator
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import kotlinx.android.synthetic.main.albums.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AlbumViewModel

    override fun layoutId() = R.layout.albums

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = viewModel(viewModelFactory) {
            albumViewState.observe {
                when (it) {
                    is GetAlbumViewState -> handleAlbums(it)
                    is ErrorAlbumViewState -> handleFailure(it)
                }
            }
            navigateViewState.observe(::handleNavigate)
            loadingViewState.observe(::handleLoading)
        }
        initView()
        getAlbums()
    }

    private fun initView() {
        albumsView.layoutManager = LinearLayoutManager(context)
        albumsView.adapter = adapter
        adapter.onCreateViewHolder = { viewHolder ->
            // Pipe the View actions to the ViewModel
            viewHolder.actionStream.subscribe(viewModel.actionSteam::onNext).track()
        }
        emptyView.gone()
        retryLayout.gone()
        progressView.gone()
    }

    private fun getAlbums() {
        viewModel.actionSteam.onNext(GetAlbumAction(false))
    }

    private fun handleAlbums(viewState: GetAlbumViewState) {
        if (viewState.albums.isEmpty()) {
            emptyView.visible()
            albumsView.gone()
        } else {
            emptyView.gone()
            val count = adapter.itemCount
            adapter.addAll(viewState.albums.map { AlbumViewData(it) })
            adapter.notifyItemRangeInserted(count, viewState.albums.size)
        }
    }

    private fun handleFailure(viewState: ErrorAlbumViewState) {
        retryLayout.visible()
        errorView.text = getString(viewState.errorMessage)
        retryView.setOnClickListener { viewModel.actionSteam.onNext(GetAlbumAction(true)) }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            progressView.visible()
        } else {
            progressView.gone()
        }
        retryLayout.gone()
        emptyView.gone()
    }

    private fun handleNavigate(navigator: Navigator) {
        navigator.launchFragment(this)
    }
}