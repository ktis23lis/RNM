package com.example.rnm.di.annotation

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Retention
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value : KClass<out ViewModel>)