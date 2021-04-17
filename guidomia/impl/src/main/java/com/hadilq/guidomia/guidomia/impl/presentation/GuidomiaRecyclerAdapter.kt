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
import com.hadilq.guidomia.guidomia.impl.databinding.ProsConsItemBinding
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
  private val carItemOnClick: CarItemOnClick,
) : RecyclerView.ViewHolder(binding.root) {

  private val proConsBindingPool = ProConsItemPool(binding.root)

  fun bind(car: CarModel) = binding.apply {
    ivCar.setImageDrawable(ContextCompat.getDrawable(root.context, car.image.value))
    tvTitle.text = root.context.getString(
      R.string.car_item_title, car.make.value, car.model.value
    )
    tvPrice.text =
      binding.root.context.getString(R.string.car_item_price, (car.price.value / 1000).toInt())
    ivStar1.visibility = if (car.rate.value >= 1) View.VISIBLE else View.GONE
    ivStar2.visibility = if (car.rate.value >= 2) View.VISIBLE else View.GONE
    ivStar3.visibility = if (car.rate.value >= 3) View.VISIBLE else View.GONE
    ivStar4.visibility = if (car.rate.value >= 4) View.VISIBLE else View.GONE
    ivStar5.visibility = if (car.rate.value >= 5) View.VISIBLE else View.GONE

    if (car.collapsed) {
      collapsibles.visibility = View.GONE
    } else {
      collapsibles.visibility = View.VISIBLE

      llPros.removeAllViews()
      llCons.removeAllViews()
      proConsBindingPool.releaseAll()
      if (car.pros.isEmpty()) {
        tvPros.visibility = View.GONE
        llPros.visibility = View.GONE
      } else {
        tvPros.visibility = View.VISIBLE
        llPros.visibility = View.VISIBLE
        car.pros.forEach { pro ->
          val prosConsBinding = proConsBindingPool.newItem
          prosConsBinding.root.text = pro
          llPros.addView(
            prosConsBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
          )
        }
      }

      if (car.cons.isEmpty()) {
        tvCons.visibility = View.GONE
        llCons.visibility = View.GONE
      } else {
        tvCons.visibility = View.VISIBLE
        llCons.visibility = View.VISIBLE
        car.cons.forEach { con ->
          val prosConsBinding = proConsBindingPool.newItem
          prosConsBinding.root.text = con
          llCons.addView(
            prosConsBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
          )
        }
      }
    }
    root.setOnClickListener { carItemOnClick.onClickCar(adapterPosition) }
  }

  /**
   * To avoid inflation on each click.
   */
  private class ProConsItemPool(private val root: ViewGroup) {

    private val pool = ArrayList<ItemState>()

    val newItem: ProsConsItemBinding
      get() =
        pool.find { it.released }?.let {
          it.released = false
          it.prosConsItemBinding
        } ?: run {
          val prosConsBinding =
            ProsConsItemBinding.inflate(LayoutInflater.from(root.context), root, false)
          pool.add(ItemState(prosConsBinding, false))
          prosConsBinding
        }

    fun releaseAll() {
      pool.forEach { it.released = true }
    }

    private class ItemState(
      val prosConsItemBinding: ProsConsItemBinding,
      var released: Boolean = true
    )
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
