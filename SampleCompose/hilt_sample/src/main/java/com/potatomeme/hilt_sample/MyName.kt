package com.potatomeme.hilt_sample

import java.util.UUID
import javax.inject.Inject


//Inject : 의존을 추가하겠다
class MyName
//@Inject constructor()
{
    private val uuid = UUID.randomUUID()
    override fun toString(): String {
        return "test name $uuid"
    }
}