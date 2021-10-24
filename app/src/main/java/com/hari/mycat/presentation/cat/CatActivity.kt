package com.hari.mycat.presentation.cat

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.hari.mycat.R
import com.hari.mycat.databinding.ActivityCatBinding
import com.hari.mycat.presentation.common.extension.*
import com.hari.mycat.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBinding
    private val viewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        observeImageCat()
    }

    private fun initViews() {
        binding.btnChangeCat.setOnClickListener {
            viewModel.getCatImage()
        }
    }

    private fun observeImageCat() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getCatImage()
                viewModel.catImage.collect {
                    when (it) {
                        is Result.Loading -> renderLoading(true)
                        is Result.Data -> renderCatImage(it.data.imageCat)
                        is Result.Error -> {
                            renderLoading(false)
                            showToastLong(it.message)
                        }
                    }
                }
            }
        }
    }

    //if use LiveData
//    private fun observeLiveData(){
//        viewModel.getCatImage()
//        viewModel.catImageLive.observe(this) { result ->
//            when (result) {
//                is Result.Loading -> renderLoading(true)
//                is Result.Data -> renderCatImage(result.data.imageCat)
//                is Result.Error -> {
//                    renderLoading(false)
//                    showToastLong(result.message)
//                }
//            }
//        }
//    }

    private fun renderCatImage(pathImgCat: String?) {
        showToastShort(getString(R.string.title_loading_image_cat))
        if (!TextUtils.isEmpty(pathImgCat)) {
            Glide
                .with(this)
                .load(pathImgCat)
                .placeholder(getDrawable(R.drawable.bg_cat_waiting))
                .fallback(getDrawable(R.drawable.ic_not_found))
                .error(getDrawable(R.drawable.ic_not_found))
                .into(binding.ivCat)
            renderLoading(false)
        } else {
            showToastShort(getString(R.string.title_empty_url))
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loading.visible()
                btnChangeCat.isEnable(false)
            } else {
                loading.gone()
                btnChangeCat.isEnable(true)
            }
        }
    }
}