package com.gitlab.sample.presentation.common.di

import com.gitlab.sample.presentation.common.Navigator

interface NavigatorFactory {
    fun <T : Navigator> create(clazz: Class<T>): T
}