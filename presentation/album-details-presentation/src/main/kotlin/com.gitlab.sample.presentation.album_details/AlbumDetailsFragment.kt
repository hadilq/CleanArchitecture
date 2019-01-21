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

import androidx.paging.PagedList
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gitlab.sample.presentation.album_details.di.viewholder.AlbumViewHolderBridge
import com.gitlab.sample.presentation.album_details.recycler.AlbumDetailsAdapter
import com.gitlab.sample.presentation.album_details.rmvvm.AlbumDetailsViewModel
import com.gitlab.sample.presentation.album_details.rmvvm.GetAlbumDetailsAction
import com.gitlab.sample.presentation.common.BaseFragment
import com.gitlab.sample.presentation.common.di.NavigatorFactory
import com.gitlab.sample.presentation.common.extention.gone
import com.gitlab.sample.presentation.common.extention.visible
import com.gitlab.sample.presentation.common.navigator.AlbumDetailsNavigator
import com.gitlab.sample.presentation.common.recycler.RecyclerState
import kotlinx.android.synthetic.main.album_details.*
import javax.inject.Inject

class AlbumDetailsFragment : BaseFragment() {

    @Inject
    lateinit var adapter: AlbumDetailsAdapter
    @Inject
    lateinit var bridge: AlbumViewHolderBridge
    @Inject
    lateinit var navigatorFactory: NavigatorFactory

    private lateinit var viewModel: AlbumDetailsViewModel

    override fun layoutId() = R.layout.album_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModel(viewModelFactory) {
            val navigator = navigatorFactory.create(AlbumDetailsNavigator::class.java)
            albumId = navigator.getAlbumId(arguments)

            viewState().observe { state ->
                state.photos?.let { handleAlbum(it, state.totalCount) }
                state.loading.let(::handleLoading)
                state.error?.let { handleFailure(state.errorMessage, it) }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
    }

    private fun getMaxSpan(): Int {
        return 3
    }

    private fun getDetails() {
        viewModel.actionStream.onNext(GetAlbumDetailsAction(false))
    }

    private fun handleAlbum(photos: PagedList<RecyclerState>, totalCount: Int) {
        adapter.totalCount = totalCount
        adapter.submitList(photos)
        if (photos.isEmpty() && totalCount == 0) {
            emptyView.visible()
        } else {
            emptyView.gone()
        }
    }

    private fun handleFailure(@StringRes errorMessage: Int, @Suppress("UNUSED_PARAMETER") error: Throwable) {
        showFailure(errorMessage) { viewModel.actionStream.onNext(GetAlbumDetailsAction(true)) }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            progressView.visible()
        } else {
            progressView.gone()
        }
    }
}