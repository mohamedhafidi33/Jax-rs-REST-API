package ma.shm.hassani.entities;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class District {
	private int id;
	private String name;
	private Date created;
	private Member delegate;
	private List<Member> members;
	private List<Link> links;
	
	public District(int id, String name, Date created, Member delegate, List<Member> members) {
		super();
		this.id = id;
		this.name = name;
		this.created = created;
		this.delegate = delegate;
		this.members = members;
	}
	
}
