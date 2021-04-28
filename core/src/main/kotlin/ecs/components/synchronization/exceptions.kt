package ecs.components.synchronization

import ecs.EntityComponent
import java.lang.Exception

class NoMapperFoundInComponent(cl: Class<out EntityComponent>) :
    Exception("No mapper were found in the companion object of: ${cl.name}")