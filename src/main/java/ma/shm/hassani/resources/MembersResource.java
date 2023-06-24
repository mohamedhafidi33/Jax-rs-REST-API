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
	public Member getMemberById(@PathParam("id")Integer id) {
		 return memberStorage.getById(id);
	}
	
	@POST
	public Response createMember(Member member) {
		memberStorage.storeMember(member);
		return Response.ok().build();
	}
	
	@GET
	public List<Member> getMembers(){
		memberStorage.getMembers().forEach(m->System.out.println(m));
		return memberStorage.getMembers();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateMember(@PathParam("id")Integer id, Member member) {
		memberStorage.updateMember(id, member);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteMember(@PathParam("id") Integer id){
		memberStorage.deleteMember(id);
		return Response.accepted().build();
	}
}
