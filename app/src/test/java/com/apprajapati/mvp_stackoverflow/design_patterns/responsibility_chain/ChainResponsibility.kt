package com.apprajapati.mvp_stackoverflow.design_patterns.responsibility_chain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Chain of responsibility design pattern -
- Define a chain of handlers to process a request.
- Each handler contains a ref to the next handler
- each handler can decide whether to process or pass the request to next one

Example of this can be ConstraintLayout with diff views such as Relative, Linear, Button, text etc

When clicking on button, it needs to decide how to pass click listener to the top ConstraintLayout view tree.
 */

interface HandlerChain {
    fun addHeader(input: String) : String
}

class AuthenticationHeader(
    val token: String?,
    var next: HandlerChain ? = null
) : HandlerChain {


    override fun addHeader(input: String): String = "$input\nAuthorization: $token".let {
        next?.addHeader(it) ?: it
    }

}

class ContentTypeHeader(
    val contentType: String?,
    var next: HandlerChain ? = null
) : HandlerChain {


    override fun addHeader(input: String): String = "$input\nContentType: $contentType".let {
        next?.addHeader(it) ?: it
    }

}

class BodyPayloadHeader(
    val payload: String?,
    val next: HandlerChain ? = null
) : HandlerChain {
    override fun addHeader(input: String): String = "$input\n$payload".let {
        next?.addHeader(it) ?: it
    }
}

class ChainResponsibility {
    @Test
    fun testChainResPattern(){
        val authHeader = AuthenticationHeader("123444")
        val contentType = ContentTypeHeader("json")
        val payloadHeader = BodyPayloadHeader("Body: {\"username\" = \"ajay\"}")

        authHeader.next = contentType
        contentType.next = payloadHeader

        val authMessage = authHeader.addHeader("Headers with authentication.")
        println(authMessage)

        println("-----------------")

        val nonAuthMessage = contentType.addHeader("Headers without authentication.")
        println(nonAuthMessage)

        Assertions.assertThat(authMessage).isEqualTo(
            """
                Headers with authentication.
                Authorization: 123444
                ContentType: json
                Body: {"username" = "ajay"}
            """.trimIndent()
        )

        Assertions.assertThat(nonAuthMessage).isEqualTo(
            """
                Headers without authentication.
                ContentType: json
                Body: {"username" = "ajay"}
            """.trimIndent()
        )
    }
}