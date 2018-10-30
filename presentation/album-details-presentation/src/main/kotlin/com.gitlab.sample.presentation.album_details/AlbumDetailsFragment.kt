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
package com.gitlab.sample.presentation.album_details

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.view.View
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsInjector
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsSubComponent
import com.gitlab.sample.presentation.album_details.recycler.AlbumDetailsAdapter
import com.gitlab.sample.presentation.album_details.recycler.AlbumPhotoViewData
import com.gitlab.sample.presentation.album_details.rmvvm.*
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.BundleKey
import com.gitlab.sample.presentation.common.ViewState
import com.gitlab.sample.presentation.common.extention.filterTo
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.album_details.*
import javax.inject.Inject

class AlbumDetailsFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumDetailsAdapter
    @Inject
    lateinit var viewModelFactory: AlbumDetailsViewModelFactory

    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var subComponent: AlbumDetailsSubComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subComponent = AlbumDetailsInjector.createAlbumsComponent()
        subComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            val subject = PublishSubject.create<ViewState>()
            addDisposable(register(this@AlbumDetailsFragment, subject))

            // Avoid creating the state machine with "when(state){}" to avoid a big, unreadable block of "when clause"
            subject.filterTo(GetAlbumViewState::class.java).subscribe(::handleAlbums)
            subject.filterTo(ErrorAlbumViewState::class.java).subscribe(::handleFailure)
            subject.filterTo(LoadingViewState::class.java).subscribe(::handleLoading)
        }

        viewModel.albumId = arguments!!.getLong(BundleKey.ALBUM_ID)
    }

    override fun layoutId() = R.layout.album_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (viewModel.savedDetails.isEmpty()) {
            getDetails()
        }
    }

    private fun initView() {
        adapter.addAll(viewModel.savedDetails.map { AlbumPhotoViewData(it) })
        detailsView.layoutManager = StaggeredGridLayoutManager(getMaxSpan(), StaggeredGridLayoutManager.VERTICAL)
        detailsView.adapter = adapter

        activity?.apply {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            adapter.itemWidth = displayMetrics.widthPixels / getMaxSpan()
        } ?: let {
            /*If reach here log an assertion because it should never happen*/
        }

        adapter.onCreateViewHolder = { viewHolder ->
            // Pipe the View actions to the ViewModel
            addDisposable(viewHolder.actionStream.subscribe(viewModel.actionSteam::onNext))
        }
        emptyView.gone()
        retryLayout.gone()
        progressView.gone()
    }

    private fun getMaxSpan(): Int {
        return 3
    }

    private fun getDetails() {
        viewModel.actionSteam.onNext(GetAlbumDetailsAction)
    }

    private fun handleAlbums(viewState: GetAlbumViewState) {
        if (viewState.photos.isEmpty()) {
            emptyView.visible()
            detailsView.gone()
        } else {
            emptyView.gone()
            val count = adapter.itemCount
            adapter.addAll(viewState.photos.map {
                AlbumPhotoViewData(
                        it
                )
            })
            adapter.notifyItemRangeInserted(count, viewState.photos.size)
        }
    }

    private fun handleFailure(viewState: ErrorAlbumViewState) {
        retryLayout.visible()
        errorView.text = getString(viewState.errorMessage)
        retryView.setOnClickListener { getDetails() }
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
}