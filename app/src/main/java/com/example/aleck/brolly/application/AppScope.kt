package com.example.aleck.brolly.application

import javax.inject.Qualifier
import javax.inject.Scope

/**
 * The scope annotation class will allow
 * dependencies generated with this annotation to be
 * treated as singletons
 */
@Scope
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope