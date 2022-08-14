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
    val chats = mutableMapOf<Int, Chat>() // в Chat храняться соо от обоих пользователей(входящие и исходящие)

    // с отправлением первого соо создается сам чат
    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat(mutableListOf()) }.messages += message
    }

    // удаление чата, которое приводит к удалению всех сообщений
    fun deleteChat(userId: Int): Chat? {
        return chats.remove(userId)
    }

    fun editMessages(userId: Int, oldMessage: Message, message: Message) {
        val index = chats[userId]?.messages?.indexOf(oldMessage)
        if (index != null) {
            chats[userId]?.messages?.set(index, message)
        }// поменять старое значение на новое, но оно учитывает оно прочитанное или нет ПОДУМАТЬ можно ли сделать так, чтобы прочитанность соо игнорировалась
    }

    // Удалить сообщение (при удалении всех сообщений в чате весь чат удаляется).
    fun deleteMessages(userId: Int, message: Message) {
        chats[userId]?.messages?.remove(message)
        if (chats[userId]?.messages?.isEmpty() == true) {
            chats.remove(userId)
        }
    }

    // Получить список чатов (например, service.getChats) ЕСЛИ НЕТ СОО ТО ПИСАТЬ "нет сообщений"
    @JvmName("getChats1")
    fun getChats(): MutableMap<Int, Chat>? {
        return chats
    }

    //Получить информацию о количестве непрочитанных чатов - например для уведомления
    fun getUnreadChatsCount(): Int {
        var count = 0
       chats.filter { getUnreadMessagesCount(it.key)>0 }.forEach{ _ -> count ++}
        return count
    }

    fun getUnreadMessagesCount(userId: Int): Int {
        val chat = chats[userId] ?: throw NoChatException()
        return chat.messages.filter { it.unread == true }.count()
    }

    fun getLastMessages(userId: Int): List<Message>? { //изменил статус соо на прочитанные
        val chat = chats[userId] ?: throw NoChatException()
        return chat.messages.filter { true }.takeLast(10).onEach { it.unread = false }
//выбрасывать исключение если нет никаких сообщений -?
        // присвоили значение прочитанно c помощью onEach
    }

}

fun main() {

    val service = ChatService()
    println(service.chats)
    repeat(7) {
        service.addMessage(1, Message("msg $it", unread = true))
    }
    service.addMessage(2, Message("second", 1, true))

    println(service.chats)
    repeat(7) {
        service.editMessages(1, Message("msg $it"), Message("text $it"))
    }
    println(service.chats)
    service.deleteMessages(1, Message("text 2"))
    println(service.chats)
    service.deleteChat(1)
    println(service.getChats())
  println(service.getLastMessages(2))
//    println(service.chats)
//    println(service.getUnreadMessagesCount(2))
//    println(service.getUnreadChatsCount())

}
