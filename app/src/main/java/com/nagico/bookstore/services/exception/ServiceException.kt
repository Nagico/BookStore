package com.nagico.bookstore.services.exception

open class ServiceException : RuntimeException {
    var code: String ?= null
    constructor() : super()
    constructor(code: String) : super() {
        this.code = code
    }
    constructor(code: String, message: String) : super(message) {
        this.code = code
    }
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}