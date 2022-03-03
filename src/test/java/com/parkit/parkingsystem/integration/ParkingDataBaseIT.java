package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	private static Ticket ticket;

	@BeforeAll
	private static void setUp() throws Exception {
		ticket = new Ticket();
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown() {

	}

	@Test
	public void testParkingACar() {
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		ticket = ticketDAO.getTicket("ABCDEF");
		assertEquals(1, ticket.getId());
		assertEquals(1, ticket.getParkingSpot().getId());
		assertEquals("ABCDEF", ticket.getVehicleRegNumber());
		assertEquals(0.0, ticket.getPrice());
		assertNotNull(ticket.getInTime());
		assertTrue(ticketDAO.saveTicket(ticket));
		assertNull(ticket.getOutTime());
		// TODO: check that a ticket is actualy saved in DB and Parking table is updated
		// with availability
	}

	@Test
	public void testParkingLotExit() {
		testParkingACar();
		Ticket newTicket = new Ticket();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processExitingVehicle();
		newTicket = ticketDAO.getTicket("ABCDEF");
		assertEquals(ticket.getId(), newTicket.getId());
		assertEquals(ticket.getParkingSpot(), newTicket.getParkingSpot());
		assertEquals(ticket.getVehicleRegNumber(), newTicket.getVehicleRegNumber());
		assertEquals(ticket.getInTime(), newTicket.getInTime());
		assertNotNull(newTicket.getOutTime());
		assertTrue(ticketDAO.updateTicket(newTicket));
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}

}
