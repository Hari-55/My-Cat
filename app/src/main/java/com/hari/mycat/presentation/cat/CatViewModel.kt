package com.hari.mycat.presentation.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hari.mycat.domain.cat.entities.CatEntity
import com.hari.mycat.domain.cat.usecases.GetCatImageUseCase
import com.hari.mycat.utils.NetworkHelper
import com.hari.mycat.utils.Result
import com.hari.mycat.utils.resolveError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val getCatImageUseCase: GetCatImageUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _catImage = MutableStateFlow<Result<CatEntity>>(Result.Loading)
    val catImage: StateFlow<Result<CatEntity>> get() = _catImage

    //If use Live Data
//    private val _catImageLive= MutableLiveData<Result<CatEntity>>()
//    val catImageLive : LiveData<Result<CatEntity>> get() = _catImageLive

    fun getCatImage() {
        viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                getCatImageUseCase()
                    .onStart {
                        _catImage.value = Result.Loading
                        //_catImageLive.value = Result.Loading
                    }
                    .catch { e ->
                        if (e is Exception) _catImage.value = Result.Error(resolveError(e))
                        else _catImage.value = Result.Error("Maaf, mohon coba lagi")
                        // if (e is Exception) _catImageLive.value = Result.Error(resolveError(e))
                        // else _catImageLive.value = Result.Error("Maaf, mohon coba lagi")
                    }
                    .collect {
                        _catImage.value = it
                        // _catImageLive.value = it
                    }
            } else {
                _catImage.value = Result.Error("Internet Anda Mati")
            }
        }
    }
}