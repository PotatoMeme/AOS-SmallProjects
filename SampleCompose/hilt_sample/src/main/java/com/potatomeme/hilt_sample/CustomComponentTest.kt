package com.potatomeme.hilt_sample

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.internal.GeneratedComponentManager
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Scope
import kotlin.random.Random

data class User(val name: String)

// CustomComponent 정의
@DefineComponent(parent = ActivityComponent::class)
@DialogScope
interface DialogComponent

// CustomComponent 빌더 정의
@DefineComponent.Builder
interface DialogComponentBuilder {
    fun setLocalDateTime(
        @BindsInstance localDateTime : LocalDateTime
    ) : DialogComponentBuilder
    fun build(): DialogComponent//필수 메서드
}

class DialogComponentManager @Inject constructor(
    private val componentBuilder: DialogComponentBuilder,
) : GeneratedComponentManager<DialogComponent> {
    override fun generatedComponent(): DialogComponent {
        return componentBuilder.setLocalDateTime(LocalDateTime.now()).build()
    }
}

@EntryPoint
@InstallIn(DialogComponent::class)
interface DialogEntryPoint {
    fun getUser(): User

    fun getRandomNumber() : Int

    fun getLocalDateTime() : LocalDateTime
}

@Module
@InstallIn(DialogComponent::class)
object DialogModule {
    @Provides
    fun provideUser(): User {
        return User("testUser")
    }

    @Provides
    @DialogScope
    fun provideRandomNumber() : Int{
        return Random.nextInt()
    }
}

@Scope
annotation class DialogScope

class MyDialog @Inject constructor(
    private val context: Activity,
    private val dialogComponentManager: DialogComponentManager,
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val entryPoint = EntryPoints.get(dialogComponentManager, DialogEntryPoint::class.java)
        val user: User = entryPoint.getUser()

        val randNum1 = entryPoint.getRandomNumber()
        val randNum2 = entryPoint.getRandomNumber()
        val randNum3 = entryPoint.getRandomNumber()

        val localDateTime = entryPoint.getLocalDateTime()

        setContentView(
            TextView(context).apply {
                text = "${localDateTime.toString()}\n"+ "${user.name}${randNum1}\n"+ "${user.name}${randNum2}\n"+ "${user.name}${randNum3}\n"
            }
        )
    }
}