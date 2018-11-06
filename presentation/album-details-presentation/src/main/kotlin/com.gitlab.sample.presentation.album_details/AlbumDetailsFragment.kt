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
import com.gitlab.sample.presentation.album_details.di.viewholder.AlbumViewHolderBridge
import com.gitlab.sample.presentation.album_details.recycler.AlbumDetailsAdapter
import com.gitlab.sample.presentation.album_details.recycler.AlbumPhotoViewData
import com.gitlab.sample.presentation.album_details.rmvvm.AlbumDetailsViewModel
import com.gitlab.sample.presentation.album_details.rmvvm.ErrorAlbumViewState
import com.gitlab.sample.presentation.album_details.rmvvm.GetAlbumDetailsAction
import com.gitlab.sample.presentation.album_details.rmvvm.GetAlbumViewState
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.BundleKey
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import kotlinx.android.synthetic.main.album_details.*
import javax.inject.Inject

class AlbumDetailsFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumDetailsAdapter
    @Inject
    lateinit var bridge: AlbumViewHolderBridge

    private lateinit var viewModel: AlbumDetailsViewModel

    override fun layoutId() = R.layout.album_details


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModel(viewModelFactory) {
            albumId = arguments!!.getLong(BundleKey.ALBUM_ID)

            albumDetailsViewState.observe {
                when (it) {
                    is GetAlbumViewState -> handleAlbum(it)
                    is ErrorAlbumViewState -> handleFailure(it)
                }
            }
            loadingViewState.observe(::handleLoading)
        }
        initView()
        getDetails()
    }

    private fun initView() {
        detailsView.layoutManager = StaggeredGridLayoutManager(getMaxSpan(), StaggeredGridLayoutManager.VERTICAL)
        detailsView.adapter = adapter

        activity?.apply {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            bridge.itemWidth = displayMetrics.widthPixels / getMaxSpan()
        } ?: let { /*If reach here log an assertion because it should never happen*/ }

        adapter.onCreateViewHolder = { viewHolder ->
            // Pipe the View actions to the ViewModel
            viewHolder.actionStream.subscribe(viewModel.actionStream::onNext).track()
        }
        emptyView.gone()
        retryLayout.gone()
        progressView.gone()
    }

    private fun getMaxSpan(): Int {
        return 3
    }

    private fun getDetails() {
        viewModel.actionStream.onNext(GetAlbumDetailsAction(false))
    }

    private fun handleAlbum(viewState: GetAlbumViewState) {
        if (viewState.photos.isEmpty()) {
            emptyView.visible()
            detailsView.gone()
        } else {
            emptyView.gone()
            val count = adapter.itemCount
            adapter.addAll(viewState.photos.map {
                AlbumPhotoViewData(it)
            })
            adapter.notifyItemRangeInserted(count, viewState.photos.size)
        }
    }

    private fun handleFailure(viewState: ErrorAlbumViewState) {
        retryLayout.visible()
        errorView.text = getString(viewState.errorMessage)
        retryView.setOnClickListener { viewModel.actionStream.onNext(GetAlbumDetailsAction(true)) }
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
}