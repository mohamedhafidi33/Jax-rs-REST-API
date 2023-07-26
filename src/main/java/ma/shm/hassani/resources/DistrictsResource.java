package ma.shm.hassani.resources;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import ma.shm.hassani.entities.District;
import ma.shm.hassani.entities.Link;
import ma.shm.hassani.services.DistrictService;
import ma.shm.hassani.storage.DistrictStorage;

@Path("districts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DistrictsResource {
	DistrictStorage districtStorage = new DistrictStorage();
	DistrictService districtService = new DistrictService();
	
	@GET
	public Response getDistricts() {
		List<District> districts = districtStorage.getDistricts();
		if(districts!=null) {
		return Response.status(Status.OK).entity(districtStorage).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getDistrictById(@PathParam("id")Integer id,@Context UriInfo uriInfo) {
		District district = districtService.getDistrictById(id);
		if(district!=null) {
			String self = uriInfo.getBaseUriBuilder()
					.path(DistrictsResource.class)
					.path(Integer.toString(district.getId()))
					.build()
					.toString();
			String members = uriInfo.getBaseUriBuilder()
					.path(DistrictsResource.class)
					.path(Integer.toString(district.getId()))
					.path("/members")
					.build()
					.toString();
			district.setLinks(List.of(new Link("self",self),new Link("members",members)));
			return Response.status(Status.OK).entity(district).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@POST
	public Response createDistrict(District district) {
		if(district!=null) {
			districtService.save(district);
		return Response.noContent().build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateDistrict(@PathParam("id")Integer id,District district) {
		if(district==null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if(districtStorage.updateDistrict(id, district)) {
			return Response.ok().build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteDistrict(@PathParam("id")Integer id) {
		if(districtStorage.deleteDistrict(id)) {
			return Response.accepted().build();
			}
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@Path("/{districtId}/members")
	public MembersResource getMemberResource() {
		return new MembersResource();
	}
}