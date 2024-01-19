package com.robertrussell.miguel.openweather.viewmodel

import androidx.lifecycle.ViewModel
import com.robertrussell.miguel.openweather.model.dao.UserDao

class UserViewModel(private val userDao: UserDao) : ViewModel()  {

    private val TAG = SignViewModel::class.simpleName


}