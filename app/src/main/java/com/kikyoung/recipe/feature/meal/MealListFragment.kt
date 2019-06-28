package com.kikyoung.recipe.feature.meal

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kikyoung.recipe.R
import com.kikyoung.recipe.base.BaseFragment
import com.kikyoung.recipe.data.model.Meal
import com.kikyoung.recipe.util.extension.hide
import com.kikyoung.recipe.util.extension.observeChanges
import com.kikyoung.recipe.util.extension.show
import kotlinx.android.synthetic.main.fragment_meal_list.*

class MealListFragment : BaseFragment<MealViewModel>(MealViewModel::class) {

    private val mealListAdapter = MealListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBar()

        mealListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealListAdapter
            addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(context!!, R.drawable.meal_list_divider)!!)
            })
        }

        mealListAdapter.setOnItemClickListener(object : MealListAdapter.OnItemClickListener {
            override fun onItemClick(item: Meal) {
                viewModel.showDetails(item)
            }
        })

        viewModel.mealsLiveData().observeChanges(this) {
            if (it.isNullOrEmpty()) showEmptyView()
            else showMealList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = (searchMenuItem.actionView as SearchView)
        searchView.queryHint = getString(R.string.search_recipes_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMenuItem.collapseActionView()
                viewModel.searchMeals(query)
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return true
            }
        })
    }

    private fun setActionBar() {
        setHasOptionsMenu(true)
        try {
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.setSupportActionBar(mealListToolbar)
            appCompatActivity.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setTitle(R.string.recipe_list_title)
            }
        } catch (e: ClassCastException) {
            // Ignore for FragmentScenario tests
        }
    }

    private fun showEmptyView() {
        instructionTextView.hide()
        mealListRecyclerView.hide()
        emptyTextView.show()
    }

    private fun showMealList(mealList: List<Meal>) {
        mealListAdapter.setMealList(mealList)
        instructionTextView.hide()
        emptyTextView.hide()
        mealListRecyclerView.show()
    }
}