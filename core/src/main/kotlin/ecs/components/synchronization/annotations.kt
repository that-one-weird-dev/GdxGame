package ecs.components.synchronization

@Target(AnnotationTarget.FIELD)
annotation class Sync


/**
 * Must be given to a method that has data of type Map<String, Any?> as the only parameter
 */
@Target(AnnotationTarget.FUNCTION)
annotation class OnSync
