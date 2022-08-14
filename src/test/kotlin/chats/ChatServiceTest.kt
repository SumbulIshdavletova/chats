package chats

import org.junit.Assert.assertEquals
import org.junit.Test

class ChatServiceTest {
    private val service = ChatService()

    @Test
    fun getChats() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.getChats()
    }

    @Test
    fun addMessage() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
    }

    @Test
    fun deleteChat() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.deleteChat(1)
    }

    @Test
    fun editMessages() {
        service.addMessage(1, Message("text", 1, true))
        service.addMessage(2, Message("message", 1, true))
        service.editMessages(1, Message("text"), Message("TEXT EDIT"))
    }

    @Test
    fun deleteMessages() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.deleteMessages(1, Message("message"))
    }

    @Test
    fun getChats1() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.getChats()
    }

    @Test
    fun getUnreadChatsCount() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        val count = service.getUnreadChatsCount()
        assertEquals(1, count)
    }

    @Test
    fun getUnreadMessagesCount() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        val count = service.getUnreadMessagesCount(2)
        assertEquals(1, count)
    }

    @Test(expected = NoChatException::class)
    fun getUnreadMessagesCountException() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        val count = service.getUnreadMessagesCount(3)
        assertEquals(1, count)
    }

    @Test
    fun getLastMessages() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.getLastMessages(2)
    }

    @Test(expected = NoChatException::class)
    fun getLastMessagesException() {
        service.addMessage(1, Message("text", 1, false))
        service.addMessage(2, Message("message", 1, true))
        service.getLastMessages(3)
    }

}
