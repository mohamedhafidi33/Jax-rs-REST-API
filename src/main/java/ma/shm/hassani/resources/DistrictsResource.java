package ma.shm.hassani.resources;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.shm.hassani.entities.District;
import ma.shm.hassani.entities.Grade;
import ma.shm.hassani.entities.Member;
import ma.shm.hassani.storage.DistrictStorage;

@Path("districts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DistrictsResource {
	DistrictStorage districtStorage = new DistrictStorage();
	@GET
	public District getDistrict() {
		ArrayList<Member> members = new ArrayList<>();
		members.add(new Member(1,"younes",Date.valueOf(LocalDate.now()),Grade.LEADER));
		members.add(new Member(2,"Marwan",Date.valueOf(LocalDate.now()),Grade.LEADER));
		members.add(new Member(3,"Karim",Date.valueOf(LocalDate.now()),Grade.LEADER));
		return new District(1,"SS", Date.valueOf(LocalDate.now()), new Member(1,"youness",Date.valueOf(LocalDate.now()),Grade.LEADER),members);
	}
	@POST
	public Response createDistrict(District district) {
		districtStorage.storeDistrict(district);;
		Consumer<String> sup = (x)->System.out.println(x);;
		sup.accept(district.toString());
		return Response.noContent().build();
	}
}
