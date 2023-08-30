package az.ingress.msticket.mapper

import az.ingress.msticket.dao.entity.TicketEntity
import az.ingress.msticket.dto.request.SaveTicketRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class TicketMapperTest extends Specification{
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private TicketMapper ticketMapper

    def "TestBuildToResponse"(){
        given:
        def ticket = random.nextObject(TicketEntity)

        when:
        def actual = TicketMapper.buildToResponse(ticket)

        then:
        actual.id == ticket.id
        actual.trackingCode == ticket.trackingCode
        actual.orderId == ticket.orderId
        actual.createdAt == ticket.createdAt
        actual.ticketStatus == ticket.ticketStatus
        actual.ticketDetails == ticket.ticketDetails
    }

    def "TestBuildToEntity"(){
        given:
        def request = random.nextObject(SaveTicketRequest)

        when:
        def actual = TicketMapper.buildToEntity(request)

        then:
        actual.orderId == request.orderId
        actual.ticketDetails == request.ticketDetails
        actual.trackingCode == request.trackingCode
    }
}
