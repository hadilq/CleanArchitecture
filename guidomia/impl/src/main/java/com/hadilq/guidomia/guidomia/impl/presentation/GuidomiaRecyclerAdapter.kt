package com.hadilq.guidomia.guidomia.impl.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.hadilq.guidomia.guidomia.impl.R
import com.hadilq.guidomia.guidomia.impl.databinding.CarItemBinding
import com.hadilq.guidomia.guidomia.impl.databinding.FilterItemBinding
import com.hadilq.guidomia.guidomia.impl.databinding.LineItemBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class GuidomiaRecyclerAdapter @Inject constructor(
  private val carViewHolderFactory: CarViewHolderFactory,
  private val lineViewHolderFactory: LineViewHolderFactory,
  private val filterViewHolderFactory: FilterViewHolderFactory,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val diffCallback: ItemCallback<CarListModel> = object : ItemCallback<CarListModel>() {

    override fun areItemsTheSame(oldItem: CarListModel, newItem: CarListModel): Boolean = when {
      oldItem is CarModel && newItem is CarModel -> oldItem.model == newItem.model
      oldItem is LineModel && newItem is LineModel -> true
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
      CarListType.LINE -> lineViewHolderFactory.create(
        LineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      CarListType.CAR -> carViewHolderFactory.create(
        CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      CarListType.FILTER -> filterViewHolderFactory.create(
        FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = diff.currentList[position]
    when (item.type) {
      CarListType.LINE -> Unit
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
interface LineViewHolderFactory {

  fun create(binding: LineItemBinding): LineViewHolder
}

class LineViewHolder @AssistedInject constructor(
  @Assisted private val binding: LineItemBinding,
) : RecyclerView.ViewHolder(binding.root)

@AssistedFactory
interface CarViewHolderFactory {

  fun create(binding: CarItemBinding): CarViewHolder
}

class CarViewHolder @AssistedInject constructor(
  @Assisted private val binding: CarItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(car: CarModel) {
    binding.ivCar.setImageDrawable(ContextCompat.getDrawable(binding.root.context, car.image.value))
    binding.tvTitle.text = binding.root.context.getString(
        R.string.car_item_title, car.make.value, car.model.value
      )
    binding.tvPrice.text =
      binding.root.context.getString(R.string.car_item_price, (car.price.value / 1000).toInt())
    binding.ivStar1.visibility = if (car.rate.value >= 1) View.VISIBLE else View.GONE
    binding.ivStar2.visibility = if (car.rate.value >= 2) View.VISIBLE else View.GONE
    binding.ivStar3.visibility = if (car.rate.value >= 3) View.VISIBLE else View.GONE
    binding.ivStar4.visibility = if (car.rate.value >= 4) View.VISIBLE else View.GONE
    binding.ivStar5.visibility = if (car.rate.value >= 5) View.VISIBLE else View.GONE
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
