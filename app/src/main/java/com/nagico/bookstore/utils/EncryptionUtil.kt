package com.nagico.bookstore.utils

import java.security.MessageDigest

object EncryptionUtil {
    private fun md5(input:String) :String {
        val digest = MessageDigest.getInstance("MD5")
        val result = digest.digest(input.toByteArray())
        return toHex(result)
    }

    private fun toHex(byteArray: ByteArray):String{
        val result = with(StringBuilder()){
            byteArray.forEach {
                val value = it
                val hex = value.toInt() and (0xFF)
                val hexStr = Integer.toHexString(hex)
                if(hexStr.length == 1){
                    append(0).append(hexStr)
                } else {
                    append(hexStr)
                }
            }
            toString()
        }
        return result
    }

    fun getEncryptedPassword(password:String, salt:String) :String{
        return md5(md5(password) + salt)
    }
}