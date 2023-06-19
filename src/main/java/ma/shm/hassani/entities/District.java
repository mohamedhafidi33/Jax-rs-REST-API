package ma.shm.hassani.entities;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class District {
	private String name;
	private Date created;
	private Member delegate;
	private ArrayList<Member> members;
}
