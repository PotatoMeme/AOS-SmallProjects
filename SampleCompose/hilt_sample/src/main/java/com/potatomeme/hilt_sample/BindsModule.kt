package com.potatomeme.hilt_sample

import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Inject
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindsSampleRepository(repository: SampleRepositoryImpl):SampleRepository
    @Binds
    @CustomQualifier
    abstract fun bindsCustomSampleRepository(repository: SampleRepositoryImplToCustom):SampleRepository

    @BindsOptionalOf
    abstract fun provideAAA():AAA
}
@Module
@InstallIn(SingletonComponent::class)
object TestBindsModule{
    @Provides
    fun providesAAA():AAA {
        return AAA()
    }
}

class AAA //@Inject constructor()
//@BindsOptionalOf methods cannot return unqualified types that have an @Inject-annotated constructor because those are always present
//    public abstract com.potatomeme.hilt_sample.AAA provideAAA();

interface SampleRepository{
    fun getUUID() : String
}

class SampleRepositoryImpl @Inject constructor() : SampleRepository{
    private val uuid = UUID.randomUUID()
    override fun getUUID(): String {
        return uuid.toString()
    }
}

class SampleRepositoryImplToCustom @Inject constructor() : SampleRepository{
    private val uuid = UUID.randomUUID()
    override fun getUUID(): String {
        return uuid.toString()
    }
}