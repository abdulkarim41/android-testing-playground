package com.abdulkarim.android_testing_playground.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarim.android_testing_playground.domain.usecase.GetProductsUseCase
import com.abdulkarim.android_testing_playground.domain.model.ProductApiEntity
import com.abdulkarim.android_testing_playground.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    val action: (ProductListUiAction) -> Unit = {
        when (it) {
            is ProductListUiAction.FetchProductListApi -> fetchProductListApi()
        }
    }

    private val _uiState = MutableStateFlow<ProductListUiState<Any>>(ProductListUiState.Loading(true))
    val uiState: StateFlow<ProductListUiState<Any>> = _uiState

    init { fetchProductListApi() }

    private fun fetchProductListApi() {
        viewModelScope.launch() {
            getProductsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = ProductListUiState.ProductList(result.data)
                    }
                    is Result.Error -> {
                        _uiState.value = ProductListUiState.ApiError(result.message)
                    }
                    is Result.Loading -> {
                        _uiState.value = ProductListUiState.Loading(result.loading)
                    }
                }
            }
        }
    }
}

sealed interface ProductListUiState<out ResultType> {
    data class Loading(val isLoading: Boolean) : ProductListUiState<Loading>
    data class ProductList(val data: List<ProductApiEntity>) : ProductListUiState<ProductList>
    data class ApiError(val message: String) : ProductListUiState<ApiError>
}

sealed interface ProductListUiAction {
    data object FetchProductListApi : ProductListUiAction
}
