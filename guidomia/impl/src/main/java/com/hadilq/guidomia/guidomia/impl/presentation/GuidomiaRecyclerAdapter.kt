/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hadilq.guidomia.guidomia.impl.presentation

import android.text.Editable
import android.text.TextWatcher
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
import com.hadilq.guidomia.guidomia.impl.domain.entity.MakeEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.ModelEntity
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
      oldItem is FilterModel && newItem is FilterModel -> true
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
        tvPros.visibility = View.GONE // TODO This line is not working for Mercedes Benz!!
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
  private val carItemFilter: CarItemFilter
) : RecyclerView.ViewHolder(binding.root) {

  private lateinit var currentFilter: FilterModel

  private val makeTextWatcher: TextWatcher = object : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
      carItemFilter.onNewFilter(
        currentFilter.copy(
          make = MakeEntity(s.toString()),
          cursorPosition = start + count,
          makeUpdated = true,
          modelUpdated = false,
        )
      )
    }
  }

  private val modelTextWatcher: TextWatcher = object : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
      carItemFilter.onNewFilter(
        currentFilter.copy(
          model = ModelEntity(s.toString()),
          cursorPosition = start + count,
          makeUpdated = false,
          modelUpdated = true,
        )
      )
    }
  }

  fun bind(car: FilterModel) = binding.apply {
    currentFilter = car

    filterMake.removeTextChangedListener(makeTextWatcher)
    filterMake.setText(car.make.value)
    if (car.makeUpdated) {
      filterMake.requestFocus()
      filterMake.setSelection(car.cursorPosition)
    }
    filterMake.addTextChangedListener(makeTextWatcher)

    filterModel.removeTextChangedListener(modelTextWatcher)
    filterModel.setText(car.model.value)
    if (car.modelUpdated) {
      filterModel.requestFocus()
      filterModel.setSelection(car.cursorPosition)
    }
    filterModel.addTextChangedListener(modelTextWatcher)
  }
}
