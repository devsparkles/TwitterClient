package com.devsparkle.twitterclient.presentation.casestudy

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsparkle.twitterclient.R
import com.devsparkle.twitterclient.base.resource.observeResource
import com.devsparkle.twitterclient.databinding.ActivityListCaseStudyBinding
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.presentation.casestudy.adapter.CaseStudyAdapter
import com.devsparkle.twitterclient.presentation.casestudy.viewmodel.ListCaseStudyViewModel
import com.devsparkle.twitterclient.utils.extensions.hide
import com.devsparkle.twitterclient.utils.extensions.isConnected
import com.devsparkle.twitterclient.utils.extensions.show
import com.devsparkle.twitterclient.utils.extensions.showIf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListCaseStudyActivity : AppCompatActivity() {

    private val TAG: String = "ListCaseStudy"

    private var isFirstLaunch = true
    lateinit var binding: ActivityListCaseStudyBinding
    private val viewModel by viewModel<ListCaseStudyViewModel>()
    private val adapter by inject<CaseStudyAdapter> {
        parametersOf(
            { launch: CaseStudy -> onTapListElementSelected(launch) },
        )
    }

    private fun onTapListElementSelected(cs: CaseStudy) = with(binding) {
        Toast.makeText(root.context, "Launch ${cs.teaser}", Toast.LENGTH_SHORT)
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                com.devsparkle.twitterclient.R.layout.activity_list_case_study
            )
        binding.lifecycleOwner = this
        setupToolbar()
        setupSwipeToRefresh()
        setUpRecyclerView()
        setUpResourceObserver()
        onFirstLaunch()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.title = getString(R.string.toolbar_title_default)
    }

    private fun setupSwipeToRefresh() = with(binding) {
        swipeToRefresh.setOnRefreshListener {
            viewModel.getCaseStudies(isConnected)
            loadingScreen.hide()
            emptyDataParent.hide()
            toolbar.title = getString(R.string.toolbar_title_default)
        }
    }

    private fun setUpRecyclerView() = with(binding) {
        val layoutManager =
            LinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = this@ListCaseStudyActivity.adapter
    }

    private fun setUpResourceObserver() {
        viewModel.caseStudies.observeResource(
            this@ListCaseStudyActivity,
            loading = ::onCaseStudyLoading,
            success = ::onCaseStudySuccess,
            error = ::onCaseStudyError,
            successWithoutContent = {}
        )
    }

    private fun onFirstLaunch() {
        if (isFirstLaunch) {
            viewModel.getCaseStudies(isConnected)
            isFirstLaunch = false
        }
    }

    private fun onCaseStudyLoading() = with(binding) {
        loadingScreen.showIf {
            !swipeToRefresh.isRefreshing
        }
        emptyDataParent.hide()
    }

    private fun onCaseStudySuccess(value: List<CaseStudy>?) = with(binding) {
        recyclerview.show()
        loadingScreen.hide()
        emptyDataParent.hide()
        swipeToRefresh.isRefreshing = false
        value?.let {
            it.let { caseStudies ->
                if (!isConnected) {
                    showMessage(getString(R.string.currently_offline_showing_cache_data), Toast.LENGTH_LONG)
                    toolbar.title = getString(R.string.toolbar_title_offline)
                }
                adapter.updateCaseStudies(caseStudies)
                if (caseStudies.isEmpty()) {
                    emptyDataParent.visibility = View.VISIBLE
                } else {
                    if (isConnected) {
                        viewModel.persistCaseStudies(caseStudies)
                    }
                }
            }
        } ?: run {
            showMessage("no case studies to show")
        }

    }

    private fun onCaseStudyError(exception: Exception) = with(binding) {
        loadingScreen.hide()
        toolbar.title = getString(R.string.toolbar_title_error)
        emptyDataParent.show()
        recyclerview.hide()
        showMessage("error:" + exception.message)
    }


    private fun showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) = with(binding) {
        Toast.makeText(root.context, message, duration).show()
    }
}