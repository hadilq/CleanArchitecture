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

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gitlab.sample.presentation.album.di.AlbumInjector
import com.gitlab.sample.presentation.album.di.AlbumsSubComponent
import com.gitlab.sample.presentation.album.rmvvm.*
import com.gitlab.sample.presentation.album.recycler.AlbumAdapter
import com.gitlab.sample.presentation.album.recycler.AlbumViewData
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.ViewState
import com.gitlab.sample.presentation.common.extention.filterTo
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.albums.*
import javax.inject.Inject

class AlbumFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumAdapter
    @Inject
    lateinit var viewModelFactory: AlbumViewModelFactory

    private lateinit var viewModel: AlbumViewModel
    private lateinit var subComponent: AlbumsSubComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subComponent = AlbumInjector.createAlbumsComponent()
        subComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            val subject = PublishSubject.create<ViewState>()
            addDisposable(register(this@AlbumFragment, subject))

            // Avoid creating the state machine with "when(state){}" to avoid a big, unreadable block of "when clause"
            subject.filterTo(GetAlbumViewState::class.java).subscribe(::handleAlbums)
            subject.filterTo(ErrorAlbumViewState::class.java).subscribe(::handleFailure)
            subject.filterTo(LoadingViewState::class.java).subscribe(::handleLoading)
            subject.filterTo(NavigateViewState::class.java).subscribe(::handleNavigate)
        }
    }

    override fun layoutId() = R.layout.albums

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (viewModel.savedAlbums.isEmpty()) {
            getAlbums()
        }
    }

    private fun initView() {
        adapter.addAll(viewModel.savedAlbums.map { AlbumViewData(it) })
        albumsView.layoutManager = LinearLayoutManager(context)
        albumsView.adapter = adapter
        adapter.onCreateViewHolder = { viewHolder ->
            // Pipe the View actions to the ViewModel
            addDisposable(viewHolder.actionStream.subscribe(viewModel.actionSteam::onNext))
        }
        emptyView.gone()
        retryLayout.gone()
        progressView.gone()
    }

    private fun getAlbums() {
        viewModel.getAlbums()
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
        retryView.setOnClickListener { getAlbums() }
    }

    private fun handleLoading(viewState: LoadingViewState) {
        if (viewState.loading) {
            progressView.visible()
        } else {
            progressView.gone()
        }
        retryLayout.gone()
        emptyView.gone()
    }

    private fun handleNavigate(viewState: NavigateViewState) {
        viewState.navigator.launchFragment(subComponent, this)
    }
}