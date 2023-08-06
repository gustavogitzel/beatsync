package com.beatsync

import org.springframework.beans.factory.config.BeanDefinitionCustomizer
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import java.util.Locale
import kotlin.reflect.KClass

@SpringBootApplication
@ConfigurationPropertiesScan
class BeatSyncSearch

fun rootRouter() = coRouter {
    GET("") { ok().buildAndAwait() }
}

fun beans(context: GenericApplicationContext) = beans {
    // HTTP handlers
    bean(::rootRouter)
}

private fun <T : Any> bean(context: GenericApplicationContext, type: KClass<T>) {
    context.registerBean(
        BeanDefinitionReaderUtils.uniqueBeanName(type.java.name, context),
        type.java,
        BeanDefinitionCustomizer {}
    )
}

class BeatSyncSearchInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) = beans(context).initialize(context)
}

fun main(args: Array<String>) {
    Locale.setDefault(Locale("pt", "BR"))
    runApplication<BeatSyncSearch>(*args)
}
