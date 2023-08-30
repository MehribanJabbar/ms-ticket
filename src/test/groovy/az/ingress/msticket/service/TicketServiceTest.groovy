package az.ingress.msticket.service

import az.ingress.msticket.dao.entity.TicketEntity
import az.ingress.msticket.dao.repository.TicketRepository
import az.ingress.msticket.dto.request.SaveTicketRequest
import az.ingress.msticket.enums.TicketStatus
import az.ingress.msticket.exception.NotFoundException
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class TicketServiceTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private TicketRepository ticketRepository
    private TicketService ticketService

    def setup() {
        ticketRepository = Mock()
        ticketService = new TicketService(ticketRepository)
    }

    def "TestGetTicketById success"() {
        given:
        def id = random.nextLong()
        def ticket = random.nextObject(TicketEntity)

        when:
        def actual = ticketService.getTicketById(id)

        then:
        1 * ticketRepository.findById(id) >> Optional.of(ticket)
        actual.id == ticket.id
        actual.trackingCode == ticket.trackingCode
        actual.orderId == ticket.orderId
        actual.createdAt == ticket.createdAt
        actual.ticketStatus == ticket.ticketStatus
        actual.ticketDetails == ticket.ticketDetails
    }

    def "TestGetTicketById entity not found"() {
        given:
        def id = random.nextLong()

        when:
        ticketService.getTicketById(id)

        then:
        1 * ticketRepository.findById(id) >> Optional.empty()

        NotFoundException exception = thrown()
        exception.message == "TICKET_NOT_FOUND"
    }

    def "TestGetTicketByStatus"(){
        given:
        def status = random.nextObject(TicketStatus)
        def ticket = random.nextObject(TicketEntity)

        when:
        def actual = ticketService.getTicketByStatus(status)

        then:
        1 * ticketRepository.findByTicketStatus(status)
        actual.ticketStatus == ticket.ticketStatus
        actual.id == ticket.id
        actual.trackingCode == ticket.trackingCode
        actual.orderId == ticket.orderId
        actual.createdAt == ticket.createdAt
        actual.ticketStatus == ticket.ticketStatus
        actual.ticketDetails == ticket.ticketDetails
    }

    def "TestSaveTicket"() {
        given:
        def request = random.nextObject(SaveTicketRequest)
        def ticket = TicketEntity.builder()
                .ticketDetails(request.ticketDetails)
                .trackingCode(request.trackingCode)
                .orderId(request.orderId)
                .ticketStatus(TicketStatus.CREATED)
                .build()

        when:
        ticketService.saveTicket(request)

        then:
        1 * ticketRepository.save(ticket)
        ticket.ticketDetails == request.ticketDetails
        ticket.trackingCode == request.trackingCode
        ticket.orderId == request.orderId

        ticket.ticketStatus == TicketStatus.CREATED
    }


    def "TestChangeStatus success"() {
        given:
        def id = random.nextLong()
        def status = random.nextObject(TicketStatus)
        def ticket = TicketEntity.builder()
                .ticketStatus(status)
                .build()

        when:
        ticketService.changeStatus(id, status)

        then:
        1 * ticketRepository.findById(id) >> Optional.of(ticket)
        1 * ticketRepository.save(ticket)
        ticket.ticketStatus == status
    }

    def "TestChangeStatus entity not found"(){
        given:
        def id = random.nextLong()
        def status = random.nextObject(TicketStatus)

        when:
        ticketService.changeStatus(id, status)

        then:
        1 * ticketRepository.findById(id) >> Optional.empty()

        NotFoundException exception = thrown()
        exception.message == "TICKET_NOT_FOUND"
    }

}
