package com.example.rnm.feature.personage.list

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.entity.Personage
import com.example.domain.entity.SearchPersonage
import com.example.domain.interactors.IListInteractor
import com.example.rnm.R
import com.example.rnm.databinding.FragmentPersonageListBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PersonageListFragment : BaseFragment<PersonageListViewModel>(),
    PersonageFilterFragment.DialogCallback {

    private var isFilter = false
    private lateinit var binding: FragmentPersonageListBinding
    private lateinit var personageAdapter: PersonageAdapter
    private lateinit var itemPersonageSelected: ItemPersonageSelected
    private lateinit var personageFilterFragment: PersonageFilterFragment
    private var filterPage = 1
    private var startPage = 1

    companion object {
        fun newInstance() = PersonageListFragment()
        const val PERSONAGE_LIST_TAG = "PERSONAGE_LIST_TAG"
        var filterPersonage = SearchPersonage()
        var allSizePage = 1
        var filterSizePage = 1
    }

    @Inject
    lateinit var personageInteractor: IListInteractor

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemPersonageSelected = context as ItemPersonageSelected
        personageAdapter = PersonageAdapter({ i ->
            itemPersonageSelected.onItemPersonageSelected(i)
        }, object : OnLoadNextPage {
            override fun onLoad() {
                if (!isFilter) {
                    if (startPage != allSizePage){
                        startPage++
                        viewModel.getPersonage(startPage)

                    }

                } else {
                    while (filterPage != filterSizePage){
                        filterPage++
                        viewModel.getPersonage(filterPage, filterPersonage)
                    }
                }
            }
        }
        )
        Injector.personageFragmentComponent.inject(this)
        injectViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            personageAdapter.personageList
            viewModel.getPersonage(startPage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        personageRecyclerView.adapter = personageAdapter
        viewModel.personageLiveData.observe(viewLifecycleOwner) {
            personageAdapter.personageList = it
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.internet_error), Toast.LENGTH_LONG)
                .show()
        }

        val decorator = DividerItemDecoration(context, GridLayoutManager.VERTICAL)
        decorator.setDrawable(resources.getDrawable(R.drawable.separator, null))
        personageRecyclerView.addItemDecoration(decorator)

        searchEdinText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                nameSearch(s.toString())
            }
        })
        buttonFilter.setOnClickListener {
            personageFilterFragment = PersonageFilterFragment.newInstance()
            personageFilterFragment.show(childFragmentManager, "Tag")
            isFilter = true
        }
    }

    fun nameSearch(text: String) {
        viewModel.personageLiveData.observe(viewLifecycleOwner) {
            val filterList = ArrayList<Personage>()
            for (i in it) {
                if (i.personageName.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) filterList.add(i)
            }
            personageAdapter.setDate(filterList)
        }
    }

    override fun onFilter() {
        if (filterPersonage.gender != null || filterPersonage.species != null || filterPersonage.status != null) {
            filterPage = 1
            viewModel.updateList()
            viewModel.getPersonage(filterPage, filterPersonage)
            viewModel.personageLiveData.observe(viewLifecycleOwner) {
                personageAdapter.setDate(it)
            }
        } else {
            when (personageAdapter.itemCount) {
                1 -> personageFilterFragment.dismiss()
                else -> {
                    isFilter = false
                    startPage = 1
                    viewModel.getPersonage(startPage)
                    viewModel.updateList()
                }
            }
        }
    }

    interface ItemPersonageSelected {
        fun onItemPersonageSelected(id: Int)
    }
}