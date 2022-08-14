package chats

data class Chat(val messages: MutableList<Message>)

data class Message(
    val message: String = "m",
    val senderId: Int = 1,
    var unread: Boolean = true
)
// 0 - исходящее, 1 - входящее

class NoChatException : RuntimeException()

class ChatService {
    val chats = mutableMapOf<Int, Chat>()

    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat(mutableListOf()) }.messages += message
    }

    fun deleteChat(userId: Int): Chat? {
        return chats.remove(userId)
    }

    fun editMessages(userId: Int, oldMessage: Message, message: Message) {
        val index = chats[userId]?.messages?.indexOf(oldMessage)
        if (index != null) {
            chats[userId]?.messages?.set(index, message)
        }
    }

    fun deleteMessages(userId: Int, message: Message) {
        chats[userId]?.messages?.remove(message)
        if (chats[userId]?.messages?.isEmpty() == true) {
            chats.remove(userId)
        }
    }

    @JvmName("getChats1")
    fun getChats(): MutableMap<Int, Chat>? {
        return chats
    }

    fun getUnreadChatsCount(): Int {
        var count = 0
        chats.filter { getUnreadMessagesCount(it.key) > 0 }.forEach { _ -> count++ }
        return count
    }

    fun getUnreadMessagesCount(userId: Int): Int {
        val chat = chats[userId] ?: throw NoChatException()
        return chat.messages.filter { it.unread == true }.count()
    }

    fun getLastMessages(userId: Int): List<Message>? {
        val chat = chats[userId] ?: throw NoChatException()
        return chat.messages.filter { true }.takeLast(10).onEach { it.unread = false }
    }

}

fun main() {

}
