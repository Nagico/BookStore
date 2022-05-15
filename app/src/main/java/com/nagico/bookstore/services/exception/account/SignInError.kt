package com.nagico.bookstore.services.exception.account

open class SignInError : AccountException {
    constructor() : super()
    constructor(code: String) : super(code)
    constructor(code: String, message: String) : super(code, message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}