package ecs.components.synchronization

import com.badlogic.ashley.core.Component
import java.lang.Exception

class NoMapperFoundInComponent(cl: Class<out Component>) :
    Exception("No mapper were found in the companion object of: ${cl.name}")