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
		public void storeMember(Member member) {
			this.storage();
		JSONObject jsonMember = new JSONObject(member);
		try {
			writer =new FileWriter(membersFile.getAbsolutePath());
			writer.write(jsonMember.toString());
			writer.close();
			System.out.println("tsjl!!!");
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
		return this.getMembers().stream().filter(m->m.getId()==id).toList().get(0);
	}
}
