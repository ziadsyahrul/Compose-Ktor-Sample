package com.ziad.appcomposewithktor

import com.ziad.appcomposewithktor.data.repository.UserRepository
import com.ziad.appcomposewithktor.model.Address
import com.ziad.appcomposewithktor.model.Geo
import com.ziad.appcomposewithktor.model.User
import com.ziad.appcomposewithktor.ui.viewmodel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mock<UserRepository>()
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `fetchUsers updates users state when success`() = runTest {
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
        whenever(repository.getUsers()).thenReturn(Result.success(fakeUsers))

        // When
        viewModel.fetchUser()

        // Then
        assertEquals(fakeUsers, viewModel.users.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchUsers updates error state when failure`() = runTest {
        // Given
        whenever(repository.getUsers()).thenReturn(Result.failure(RuntimeException("Network Error")))

        // When
        viewModel.fetchUser()

        // Then
        assertEquals("Network Error", viewModel.isError.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `initial state is empty`() = runTest {
        // Given
        whenever(repository.getUsers()).thenReturn(Result.success(emptyList()))

        val viewModel = UserViewModel(repository)

        // Then
        assertTrue(viewModel.users.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }
}