package entities

data class Position(var x: Float, var y: Float, var z: Float) {

    fun set(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }
}