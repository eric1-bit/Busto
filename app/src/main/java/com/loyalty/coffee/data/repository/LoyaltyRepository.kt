package com.loyalty.coffee.data.repository

import com.loyalty.coffee.data.local.dao.*
import com.loyalty.coffee.data.local.entity.*
import kotlinx.coroutines.flow.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoyaltyRepository @Inject constructor(
    private val userDao: UserDao,
    private val coffeeShopDao: CoffeeShopDao,
    private val transactionDao: TransactionDao,
    private val promoDao: PromoCampaignDao,
    private val balanceDao: BalanceDao
) {
    val currentUser = userDao.getCurrentUser()
    val allShops = coffeeShopDao.getAll()
    val connectedShops = coffeeShopDao.getConnected()
    val activePromos = promoDao.getActive()

    suspend fun seedData() {
        val mockShops = listOf(
            CoffeeShopEntity("1", "Coffee Lab МГУ", "Ленинские горы, 1", 4.8, 55.7033, 37.5296, null),
            CoffeeShopEntity("2", "Espresso Бауманка", "Рубцовская наб., 2/18", 4.5, 55.7766, 37.6826, null),
            CoffeeShopEntity("3", "Latte Art ВШЭ", "Мясницкая ул., 20", 4.7, 55.7582, 37.6329, null)
        )
        coffeeShopDao.insertAll(mockShops)

        promoDao.insertAll(
            listOf(PromoCampaignEntity("promo1", "Бесплатный кофе", "10 000 чашек для студентов", true, "students"))
        )
    }

    suspend fun registerUser(phone: String) {
        userDao.insert(
            UserEntity(
                id = UUID.randomUUID().toString(),
                phone = phone,
                name = null,
                authProvider = "phone"
            )
        )
    }

    suspend fun getShopByQr(qrContent: String): CoffeeShopEntity? {
        val shopId = qrContent.removePrefix("qr-cafe:")
        return allShops.first().find { it.id == shopId }
    }

    suspend fun getBalance(userId: String, shopId: String): BalanceEntity? {
        return balanceDao.get(userId, shopId)
    }

    suspend fun connectAndApplyPromo(shopId: String, userId: String, applyPromo: Boolean) {
        coffeeShopDao.connect(shopId)

        val currentBalance = balanceDao.get(userId, shopId)
        val newBonuses = if (applyPromo) 1 else 0
        val newStamps = if (applyPromo) 1 else 0

        balanceDao.insertOrUpdate(
            BalanceEntity(
                userId = userId,
                coffeeShopId = shopId,
                bonuses = (currentBalance?.bonuses ?: 0) + newBonuses,
                stamps = (currentBalance?.stamps ?: 0) + newStamps,
                visitsCount = (currentBalance?.visitsCount ?: 0) + 1
            )
        )

        transactionDao.insert(
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                coffeeShopId = shopId,
                type = "connect",
                amount = 0,
                description = "Подключение к кофейне",
                timestamp = System.currentTimeMillis()
            )
        )

        if (applyPromo) {
            transactionDao.insert(
                TransactionEntity(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    coffeeShopId = shopId,
                    type = "promo",
                    amount = 1,
                    description = "Бесплатный кофе (акция для студентов)",
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun getTransactions(userId: String): Flow<List<TransactionEntity>> {
        return transactionDao.getByUser(userId)
    }

    fun getBalances(userId: String): Flow<List<BalanceEntity>> {
        return balanceDao.getByUser(userId)
    }
}
