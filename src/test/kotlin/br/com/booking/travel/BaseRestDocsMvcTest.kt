package br.com.booking.travel

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("restdocs")
abstract class BaseRestDocsMvcTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    private lateinit var mapper: ObjectMapper

    lateinit var webTestClient : WebTestClient

    @BeforeEach
    fun init(restDocumentation: RestDocumentationContextProvider) {
        MockitoAnnotations.initMocks(this)

        webTestClient = WebTestClient.
        bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost")
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                .build()

        /*mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080))
                .build()*/
    }

    fun json(any: Any): String = mapper.writeValueAsString(any)
}
