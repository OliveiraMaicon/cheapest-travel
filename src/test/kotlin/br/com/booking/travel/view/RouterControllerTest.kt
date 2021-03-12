package br.com.booking.travel.view

import br.com.booking.travel.Application.Companion.PATH_ROUTE
import br.com.booking.travel.BaseRestDocsMvcTest
import br.com.booking.travel.application.RouteApplication
import br.com.booking.travel.domain.model.Route
import br.com.booking.travel.domain.service.RouteService
import br.com.booking.travel.infraestructure.helper.FileUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.LinkedMultiValueMap


@WebFluxTest(controllers = [RouteController::class])
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@Import(RouteApplication::class, RouteService::class, FileUtils::class)
class RouterControllerTest : BaseRestDocsMvcTest() {

    @MockBean
    lateinit var fileUtils: FileUtils

    var routesCsv = mutableListOf<Route>()

    @Test
    fun shouldCreateRoute() {
        val route = listOf(Route("SLC", "BRC", 30))
        println(json(route))

        mockCsv(routesCsv)

        webTestClient.put().uri(PATH_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json(route))
                .exchange().expectStatus().is2xxSuccessful.expectBody()
                .consumeWith(WebTestClientRestDocumentation.document("{ClassName}/{methodName}"))

    }

    private fun mockCsv(routesCsv: MutableList<Route>) {
        routesCsv.add(Route("GRU", "BRC", 10))
        routesCsv.add(Route("BRC", "SCL", 5))
        routesCsv.add(Route("GRU", "CDG", 75))
        routesCsv.add(Route("GRU", "SCL", 20))
        routesCsv.add(Route("GRU", "ORL", 56))
        routesCsv.add(Route("ORL", "CDG", 5))
        routesCsv.add(Route("SCL", "ORL", 20))

        Mockito.`when`(fileUtils.readCSV()).thenReturn(routesCsv)
    }


    @Test
    fun shouldGetCheapestRoute() {

        mockCsv(routesCsv)

        webTestClient.get().uri {
            it.path(PATH_ROUTE)
            it.queryParam("start", "SCL")
            it.queryParam("end", "ORL")
            it.queryParam("directed", "true")
            it.build()

        }.accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().is2xxSuccessful.expectBody()
                .consumeWith(WebTestClientRestDocumentation.document("{ClassName}/{methodName}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.requestParameters(RequestDocumentation.parameterWithName("start").description("Variável que define o ponto de partida."),
                        RequestDocumentation.parameterWithName("end").description("Variável que define o ponto de chegada."),
                                RequestDocumentation.parameterWithName("directed").description("Variavel que indica se quer rota direta."))))

    }

}