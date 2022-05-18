package com.app.appagence.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import com.app.appagence.app.model.Product
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.app.appagence.app.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocalDataStore @Inject constructor(val context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val USER_DATA = stringPreferencesKey("userDataKey")
        const val MARGARITA_PIZZA = "Margarita pizza"
        const val MARGARITA = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReDf9Ws4j9c9ofP81IwTdHjyXI0qzL6syNHg&usqp=CAU"
        const val NEAPOLITAN_PIZZA = "Neapolitan pizza"
        const val NEAPOLITAN = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSneCeNgEjaB5ZVXSP8aFQIhBBsBY9Ih_iN3Q&usqp=CAU"
        const val PEPPERONI_PIZZA = "Pepperoni pizza"
        const val PEPPERONI = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLFC_WR5ymYQkJ7EAwnn4WkY4Xko9ncg8CA95eKhF5OkFV3GDZdproXRC_j7xZB5IOYNE&usqp=CAU"
        const val DESCRIPTION = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley"
    }

    private val productList = mutableListOf(
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = MARGARITA,
            name = MARGARITA_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = PEPPERONI,
            name = PEPPERONI_PIZZA,
            description = DESCRIPTION
        ),
        Product(
            avatar = NEAPOLITAN,
            name = NEAPOLITAN_PIZZA,
            description = DESCRIPTION
        )
    )

    fun getProductList() = productList

    suspend fun setUserData(userData: String) {
        context.dataStore.edit { settings ->
            settings[USER_DATA] = userData
        }
    }

    fun getUserData(): User = runBlocking {
        Gson().fromJson(context.dataStore.data.map { preferences ->
            preferences[USER_DATA] ?: ""
        }.first(), User::class.java)
    }
}