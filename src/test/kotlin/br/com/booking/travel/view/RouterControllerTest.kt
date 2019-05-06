package br.com.booking.travel.view

import br.com.booking.travel.Application.Companion.PATH_ROUTE
import br.com.booking.travel.ApplicationTests
import br.com.booking.travel.domain.model.Route
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import java.util.*


@SpringBootTest
class RouterControllerTest : ApplicationTests() {


    @Test
    fun shouldCreateRoute(){
        val route = listOf(Route("SLC","BRC",30))
                println(json(route))
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(route)))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                PayloadDocumentation.requestFields(PayloadDocumentation.fieldWithPath("[]").description("Representação de lista de rotas."),
                                        PayloadDocumentation.fieldWithPath("[].start").description("Rota de entrada."),
                                        PayloadDocumentation.fieldWithPath("[].end").description("Rota de saída."),
                                        PayloadDocumentation.fieldWithPath("[].value").description("Valor da rota"))
                        ))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }



    @Test
    fun shouldGetCheapestRoute(){
        val params = LinkedMultiValueMap<String, String>()
        params.add("start", "GRU")
        params.add("end", "CDG")
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.requestParameters(RequestDocumentation.parameterWithName("start").description("Variável que define o ponto de partida."),
                                RequestDocumentation.parameterWithName("end").description("Variável que define o ponto de chegada."))
                ))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

}