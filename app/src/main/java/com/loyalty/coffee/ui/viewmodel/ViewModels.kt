package com.loyalty.coffee.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyalty.coffee.data.local.entity.CoffeeShopEntity
import com.loyalty.coffee.data.repository.LoyaltyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LoyaltyRepository
) : ViewModel() {

    val shops = repository.allShops.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val connectedShops = repository.connectedShops.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val user = repository.currentUser.stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        viewModelScope.launch {
            repository.seedData()
        }
    }
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: LoyaltyRepository
) : ViewModel() {

    val user = repository.currentUser.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun register(phone: String) = viewModelScope.launch {
        repository.registerUser(phone)
    }
}

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repository: LoyaltyRepository
) : ViewModel() {

    val user = repository.currentUser.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val balances = user.flatMapLatest { u ->
        u?.let { repository.getBalances(it.id) } ?: flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val transactions = user.flatMapLatest { u ->
        u?.let { repository.getTransactions(it.id) } ?: flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

@HiltViewModel
class ScanResultViewModel @Inject constructor(
    private val repository: LoyaltyRepository
) : ViewModel() {

    val user = repository.currentUser.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _shop = MutableStateFlow<CoffeeShopEntity?>(null)
    val shop: StateFlow<CoffeeShopEntity?> = _shop.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _hasPromo = MutableStateFlow(false)
    val hasPromo: StateFlow<Boolean> = _hasPromo.asStateFlow()

    private val _balance = MutableStateFlow(0)
    val balance: StateFlow<Int> = _balance.asStateFlow()

    fun processQr(qrContent: String, userId: String) = viewModelScope.launch {
        val foundShop = repository.getShopByQr(qrContent)
        _shop.value = foundShop

        foundShop?.let { s ->
            val bal = repository.getBalance(userId, s.id)
            _isConnected.value = bal != null
            _balance.value = bal?.bonuses ?: 0

            val promos = repository.activePromos.first()
            _hasPromo.value = promos.isNotEmpty() && bal == null
        }
    }

    fun confirm(userId: String, applyPromo: Boolean) = viewModelScope.launch {
        _shop.value?.let { shop ->
            repository.connectAndApplyPromo(shop.id, userId, applyPromo)
            _isConnected.value = true
            _balance.value = if (applyPromo) 1 else 0
            _hasPromo.value = false
        }
    }
}
