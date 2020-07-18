package com.dahafa.hais.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dahafa.hais.Constants;
import com.dahafa.hais.manager.PersonService;
import com.dahafa.hais.manager.RoomService;
import com.dahafa.hais.manager.StayService;
import com.dahafa.hais.model.Person;
import com.dahafa.hais.model.Room;


@RequestScoped
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientWebservice {

	private PersonService patientService;
	private StayService stayService;
	private RoomService roomService;

	private InitialContext context;
	{
		try {
			this.context = new InitialContext();

			this.patientService = (PersonService) this.context.lookup(Constants.PATIENT_SERVICE);
			this.stayService = (StayService) this.context.lookup(Constants.STAY_SERVICE);
			this.roomService = (RoomService) this.context.lookup(Constants.ROOM_SERVICE);

		} catch (final NamingException exception) {

			exception.printStackTrace();
		}
	}


	@GET
	@Path("/patients")
	public List<Person> listPatients() {
		return this.patientService.list();
	}

	@GET
	@Path("/rooms/{roomID}/patients")
	public List<Person> listByRoom(@PathParam("roomID") final long roomID) {
		return this.stayService.listPatientsByRoom(roomID);
	}

	@POST
	@Path("/rooms/search/patients")
	public List<Person> listByRoom(final Map<String, String> body) {
		final List<Person> patients = new ArrayList<>();
		final List<Room> allRooms = this.roomService.list();

		final double latitude = Double.parseDouble(body.get("latitude"));
		final double longitude = Double.parseDouble(body.get("longitude"));

		for(final Room room : allRooms) {
			if(room.contains(latitude, longitude)) {
				System.out.println(room.getID());
				patients.addAll(this.stayService.listPatientsByRoom(room.getID()));
			}
		}

		return patients;
	}
}
