package com.robertrussell.miguel.openweather

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.robertrussell.miguel.openweather.domain.AppDataBase
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserDaoTest {

    lateinit var database: AppDataBase
    lateinit var userDao: UserDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    @Test
    fun insertUser() = runBlocking {
        val user = User(
            name = "test name",
            email = "email@email.com",
            username = "username",
            password = "password"
        )
        userDao.insertUser(user)

        val result = userDao.getUser(user.username, user.password)
        Assert.assertEquals("username", result.username)
        Assert.assertEquals("password", result.password)
    }

    @Test
    fun insertUser_multiple() = runBlocking {
        val users: List<User> = listOf(
            User(
                name = "test name 01",
                email = "email01@email.com",
                username = "username01",
                password = "password01"
            ),
            User(
                name = "test name 02",
                email = "email02@email.com",
                username = "username02",
                password = "password02"
            )
        )
        for (user in users) {
            userDao.insertUser(user)
        }

        val result = userDao.getAllUsers()
        Assert.assertEquals(2, result.size)

        // Check user data from result list.
        for (resultUser in result) {
            Assert.assertNotNull(resultUser.name)
            Assert.assertNotNull(resultUser.email)
            Assert.assertNotNull(resultUser.username)
            Assert.assertNotNull(resultUser.password)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }
}
