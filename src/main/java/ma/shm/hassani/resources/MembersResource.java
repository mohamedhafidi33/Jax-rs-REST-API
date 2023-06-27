package ma.shm.hassani.resources;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.shm.hassani.entities.Member;
import ma.shm.hassani.storage.MemberStorage;

@Path("members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MembersResource {
	MemberStorage memberStorage= new MemberStorage();
	
	@GET
	@Path("/{id}")
	public Response getMemberById(@PathParam("id")Integer id) {
		if(memberStorage.getById(id)!=null) {
		 return Response.status(Response.Status.OK).entity(memberStorage.getById(id)).build();}
		else return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@POST
	public Response createMember(Member member) {
		if(member!=null) {
		memberStorage.storeMember(member);
		return Response.status(Response.Status.CREATED).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@GET
	public Response getMembers(){
		List<Member> members = memberStorage.getMembers();
		if(members!=null) {
			return Response.status(Response.Status.OK).entity(members).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateMember(@PathParam("id")Integer id, Member member) {
		if(memberStorage.updateMember(id, member)) {
		return Response.ok().build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteMember(@PathParam("id") Integer id){
		if(memberStorage.deleteMember(id)) {
		return Response.accepted().build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
