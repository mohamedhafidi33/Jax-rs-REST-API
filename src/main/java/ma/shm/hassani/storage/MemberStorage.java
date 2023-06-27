package ma.shm.hassani.storage;

import ma.shm.hassani.entities.Grade;
import ma.shm.hassani.entities.Member;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.json.JSONArray;


public class MemberStorage {
	private File membersFile;
	FileWriter writer;
	
	public void storage() {
		File dir = new File("C://Users//hp//WorkspaceEclipseIDE//hassani/data");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		membersFile = new File("C://Users//hp//WorkspaceEclipseIDE//hassani/data/membersFile.json");
		if(!membersFile.exists()) {
			try {
				membersFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		public void storeMember(Member newMember) {
			this.storage();
			ArrayList<Member> members = getMembers();
			members.add(newMember);
				try {
					writer =new FileWriter(membersFile.getAbsolutePath());
					writer.write("[");
					for(Member member:members) {
					JSONObject jsonMember = new JSONObject(member);
					writer.write(jsonMember.toString());
					writer.write("\n,");
					}
					writer.write("]");
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		public void storeMembers(List <Member> members) {
			this.storage();
				try {
					writer =new FileWriter(membersFile.getAbsolutePath());
					writer.write("[");
					for(Member member:members) {
						JSONObject jsonMember = new JSONObject(member);
					writer.write(jsonMember.toString());
					writer.write("\n,");
					}
					writer.write("]");
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	public ArrayList<Member> getMembers(){
		this.storage();
		JSONArray jsonArray = null;
		ArrayList<Member> members = new ArrayList<>();
		try {
			String contents = new String((Files.readAllBytes(Paths.get(membersFile.getAbsolutePath()))));
			jsonArray = new JSONArray(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Member member = new Member(jsonObj.getInt("id"),
									   jsonObj.getString("name"),
									   Date.valueOf(jsonObj.getString("involved")),
									   Grade.valueOf(jsonObj.getString("grade")));
			members.add(member);
		}
		return members;
	}
	public Member getById(Integer id) {
	 List<Member> members = getMembers().stream().filter(m->m.getId()==id).toList();
	 if(members.size()==0) {
		 return null;
	 }
	 else return members.get(0);
	}
	
	public boolean updateMember(Integer id, Member member) {
		Member newMember = getById(id);
		if(newMember!=null) {
		newMember.setName(member.getName());
		newMember.setGrade(member.getGrade());
		newMember.setInvolved(member.getInvolved());
		List<Member> members = getMembers();
		members.removeIf(m->m.getId()==id);
		members.forEach(m->System.out.println(m));
		storeMembers(members);
		storeMember(newMember);
		return true;
		}
		return false;
	}
	public boolean deleteMember(Integer id) {
		List<Member> members = getMembers();
		boolean action = members.removeIf(m->m.getId()==id);
		storeMembers(members);
		return action;
	}
}
