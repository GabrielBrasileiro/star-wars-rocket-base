package com.universodoandroid.starwarsjetpack.shared.extensions

import com.universodoandroid.starwarsjetpack.shared.mapper.Mapper
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

inline fun <reified I, reified O> Module.mapper(
    noinline definition: Definition<Mapper<I, O>>
): BeanDefinition<Mapper<I, O>> {
    return factory(qualifier = named(identifier<I, O>()), definition = definition)
}

inline fun <reified I, reified O> Scope.getMapper(): Mapper<I, O> {
    return get(named(identifier<I, O>()))
}

@KoinApiExtension
inline fun <reified I, reified O> KoinComponent.injectMapper(): Lazy<Mapper<I, O>> {
    return inject(named(identifier<I, O>()))
}
