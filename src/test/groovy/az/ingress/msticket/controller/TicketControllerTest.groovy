package az.ingress.msticket.controller

import az.ingress.msticket.dto.request.SaveTicketRequest
import az.ingress.msticket.dto.response.TicketResponse
import az.ingress.msticket.enums.TicketStatus
import az.ingress.msticket.service.TicketService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class TicketControllerTest extends Specification {
    private TicketService ticketService
    private MockMvc mockMvc

    def setup() {
        ticketService = Mock()
        def ticketController = new TicketController(ticketService)
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build()
    }

    def "TestGetTicketById"() {
        given:
        def id = 1L
        def url = "/v1/tickets/$id"

        def responseView = new TicketResponse(1L, "12345",
                1L, LocalDateTime.of(2021, 9, 1, 10, 30), TicketStatus.CREATED, "Ticket Details")

        def expectedResponse = '''
                {
                    "id" : 1,
                    "trackingCode" : "12345",
                    "orderId" : 1,
                    "createdAt": [2021, 9, 1, 10, 30],
                    "ticketStatus" : "CREATED",
                    "ticketDetails" : "Ticket Details"
                }
        '''

        when:
        def result = mockMvc.perform(get(url)).andReturn()

        then:
        1 * ticketService.getTicketById(id) >> responseView

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }

    def "TestSaveTicket"() {
        given:
        def url = "/v1/tickets"

        def request = new SaveTicketRequest("Ticket Details", "12345",
                1L)

        def requestBody = '''
                {
                    "ticketDetails" : "Ticket Details",
                    "trackingCode" : "12345",
                    "orderId" : 1
                }
        '''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn()

        then:
        1 * ticketService.saveTicket(request)

        def response = result.response
        response.status == HttpStatus.CREATED.value()
    }

//    def "TestChangeStatus"() {
//        given:
//        def id = 1L
//        def url = "/v1/tickets/$id/status"
//
//        def status = TicketStatus.CREATED
//
//        def expectedResponse = '''
//                {
//                    "status" : "CREATED"
//                }
//        '''
//
//        when:
//        def result = mockMvc.perform(put(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(expectedResponse))
//                .andReturn()
//
//        then:
//        1 * ticketService.changeStatus(id, status)
//
//        def response = result.response
//        response.status == HttpStatus.OK.value()
//    }
}
