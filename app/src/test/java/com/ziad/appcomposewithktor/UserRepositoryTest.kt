package com.ziad.appcomposewithktor

import com.ziad.appcomposewithktor.data.remote.UserApiService
import com.ziad.appcomposewithktor.data.repository.UserRepository
import com.ziad.appcomposewithktor.model.Address
import com.ziad.appcomposewithktor.model.Geo
import com.ziad.appcomposewithktor.model.User
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserRepositoryTest {

    private val apiService = mock<UserApiService>()
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        repository = UserRepository(apiService)
    }

    @Test
    fun `getUsers returns success when api call succeeds`() = runTest {
        // Given
        val fakeUsers = listOf(
            User(
                id = 1,
                name = "Ziad",
                email = "ziad@gmail.com",
                phone = "123456",
                website = "ziad.com",
                address = Address(
                    street = "Jl. Sudirman",
                    suite = "No. 1",
                    city = "Jakarta",
                    zipcode = "12345",
                    geo = Geo(lat = "-6.2", lng = "106.8")
                )
            )
        )
        whenever(apiService.getUsers()).thenReturn(fakeUsers)

        // When
        val result = repository.getUsers()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeUsers, result.getOrNull())
    }

    @Test
    fun `getUsers returns failure when api call throws exception`() = runTest {
        // Given
        whenever(apiService.getUsers()).thenThrow(RuntimeException("Network Error"))

        // When
        val result = repository.getUsers()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }
}