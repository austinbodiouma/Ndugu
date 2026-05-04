package com.example.ndugu.routes

import com.example.ndugu.service.MessageService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class StartChatRequest(val participantIds: List<String>, val title: String? = null)

@Serializable
data class SendMessageRequest(val content: String)

fun Route.messageRoutes(messageService: MessageService) {
    authenticate("auth-jwt") {
        route("/api/messages") {
            get("/conversations") {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)
                
                val conversations = messageService.getConversations(studentId)
                call.respond(conversations)
            }

            get("/conversations/{id}") {
                val conversationId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val messages = messageService.getMessages(conversationId)
                call.respond(messages)
            }

            post("/conversations/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val senderId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val conversationId = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val request = call.receive<SendMessageRequest>()
                
                val messageId = messageService.sendMessage(senderId, conversationId, request.content)
                call.respond(HttpStatusCode.Created, mapOf("id" to messageId))
            }

            post("/conversations") {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<StartChatRequest>()
                val participants = request.participantIds.toMutableList()
                if (!participants.contains(studentId)) participants.add(studentId)
                
                val conversationId = messageService.startConversation(participants, request.title)
                call.respond(HttpStatusCode.Created, mapOf("id" to conversationId))
            }
        }
    }
}
