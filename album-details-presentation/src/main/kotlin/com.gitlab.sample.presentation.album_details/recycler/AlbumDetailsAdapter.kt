package com.gitlab.sample.presentation.album_details.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsScope
import com.gitlab.sample.presentation.common.BaseViewHolder
import com.gitlab.sample.presentation.common.ViewData
import com.gitlab.sample.presentation.common.extention.inflate
import javax.inject.Inject

/**
 * Created by Hadi on 9/21/18.
 */
@AlbumDetailsScope
class AlbumDetailsAdapter @Inject constructor() : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val list = mutableListOf<ViewData>()
    lateinit var onCreateViewHolder: (BaseViewHolder<*>) -> Unit
    var itemWidth: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewHolder = AlbumPhotoViewHolder(parent.inflate(viewType), itemWidth)
        onCreateViewHolder.invoke(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as AlbumPhotoViewHolder).onBind(list[position] as AlbumPhotoViewData)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(data: List<ViewData>) {
        list.addAll(data)
    }

    fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}