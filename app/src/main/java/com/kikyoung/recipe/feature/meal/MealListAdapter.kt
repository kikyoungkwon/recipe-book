package com.kikyoung.recipe.feature.meal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kikyoung.recipe.data.model.Meal
import kotlinx.android.synthetic.main.item_meal.view.*

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.ViewHolder>() {

    private val meals = mutableListOf<Meal>()
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(com.kikyoung.recipe.R.layout.item_meal, parent, false))

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(meals[position])

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(meal: Meal) {
            view.apply {
                nameTextView.text = meal.name
                categoryTextView.text = meal.category
                areaTextView.text = meal.area
                setOnClickListener {
                    itemClickListener?.onItemClick(meal)
                }
            }
            Glide.with(view.context)
                .load(meal.thumbImageUrl)
                .apply(RequestOptions.fitCenterTransform())
                .into(view.thumbImageView)
        }
    }

    fun setMealList(newList: List<Meal>) {
        meals.clear()
        meals.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: Meal)
    }
}