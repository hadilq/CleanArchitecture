package com.hadilq.guidomia.guidomia.impl.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.hadilq.guidomia.guidomia.impl.databinding.CarItemBinding
import com.hadilq.guidomia.guidomia.impl.databinding.FilterItemBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class GuidomiaRecyclerAdapter @Inject constructor(
  private val carViewHolderFactory: CarViewHolderFactory,
  private val filterViewHolderFactory: FilterViewHolderFactory,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val diffCallback: ItemCallback<CarListModel> = object : ItemCallback<CarListModel>() {

    override fun areItemsTheSame(oldItem: CarListModel, newItem: CarListModel): Boolean = when {
      oldItem is CarModel && newItem is CarModel -> oldItem.model == newItem.model
      else -> false
    }

    override fun areContentsTheSame(oldItem: CarListModel, newItem: CarListModel): Boolean =
      oldItem == newItem
  }

  private val diff = AsyncListDiffer(this, diffCallback)

  fun submitList(list: List<CarListModel>) {
    diff.submitList(list)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (CarListType.values()[viewType]) {
      CarListType.CAR -> carViewHolderFactory.create(
        CarItemBinding.inflate(LayoutInflater.from(parent.context))
      )
      CarListType.FILTER -> filterViewHolderFactory.create(
        FilterItemBinding.inflate(LayoutInflater.from(parent.context))
      )
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = diff.currentList[position]
    when (item.type) {
      CarListType.CAR -> (holder as CarViewHolder).bind(item as CarModel)
      CarListType.FILTER -> (holder as FilterViewHolder).bind(item as FilterModel)
    }
  }

  override fun getItemCount(): Int = diff.currentList.size

  override fun getItemViewType(position: Int): Int {
    return diff.currentList[position].type.index
  }
}

@AssistedFactory
interface CarViewHolderFactory {

  fun create(binding: CarItemBinding): CarViewHolder
}

class CarViewHolder @AssistedInject constructor(
  @Assisted private val binding: CarItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(car: CarModel) {

  }
}

@AssistedFactory
interface FilterViewHolderFactory {

  fun create(binding: FilterItemBinding): FilterViewHolder
}

class FilterViewHolder @AssistedInject constructor(
  @Assisted private val binding: FilterItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(car: FilterModel) {

  }
}
