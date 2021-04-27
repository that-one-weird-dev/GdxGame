package ecs.components.synchronization

/**
 * If used in java you must also implement a setter for the
 * field with the name like this: setFieldname,
 * where Fieldname is the name of the field.
 *
 * And in kotlin it must be a var and not a val
 */
@Target(AnnotationTarget.FIELD)
annotation class Sync

@Target(AnnotationTarget.FUNCTION)
annotation class SyncMethod
