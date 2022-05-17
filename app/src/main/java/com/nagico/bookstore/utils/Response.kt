package com.nagico.bookstore.utils

import kotlinx.coroutines.CoroutineScope

class Response<T> {

    var code: Int = -1
    lateinit var bean: Bean<T>
    lateinit var viewModelScope: CoroutineScope

}