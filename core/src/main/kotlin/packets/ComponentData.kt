package packets

import ecs.EntityComponent

data class ComponentData(val type: Class<out EntityComponent>, val fields: Map<String, Any?>)
