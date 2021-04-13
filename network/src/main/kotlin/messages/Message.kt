package messages

abstract class Message {
    abstract fun pack(): ByteArray;
}