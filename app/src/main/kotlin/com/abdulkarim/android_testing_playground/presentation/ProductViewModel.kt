package com.abdulkarim.android_testing_playground.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkarim.android_testing_playground.domain.GetProductsUseCase
import com.abdulkarim.android_testing_playground.domain.ProductApiEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductApiEntity>>(emptyList())
    val products: StateFlow<List<ProductApiEntity>> = _products

    fun loadProducts() {
        viewModelScope.launch(dispatcher) {
            _products.value = getProductsUseCase()
        }
    }
}
