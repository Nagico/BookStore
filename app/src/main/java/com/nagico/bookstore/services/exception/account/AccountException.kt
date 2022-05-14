package com.nagico.bookstore.services.exception.account

import com.nagico.bookstore.services.exception.ServiceException

open class AccountException : ServiceException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}