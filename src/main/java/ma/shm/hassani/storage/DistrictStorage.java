package ma.shm.hassani.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.json.JsonObject;
import ma.shm.hassani.entities.District;
import ma.shm.hassani.entities.Grade;
import ma.shm.hassani.entities.Member;

public class DistrictStorage {
	private File districtsFile;
	FileWriter writer;
	
	public void storage() {
		File dir = new File("C://Users//hp//WorkspaceEclipseIDE//hassani/data");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		districtsFile = new File("C://Users//hp//WorkspaceEclipseIDE//hassani/data/districtsFile.json");
		if(!districtsFile.exists()) {
			try {
				districtsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void storeDistrict(District newDistrict ) {
		this.storage();
		ArrayList<District> districts = getDistricts();
		districts.add(newDistrict);
	
	try {
		writer =new FileWriter(districtsFile.getAbsolutePath());
		writer.write("[");
		for(District district:districts) {
		JSONObject jsonDistrict = new JSONObject(district);
		writer.write(jsonDistrict.toString());
		writer.write("\n,");
		}
		writer.write("]");
		writer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	public void storeDistricts(List<District> districts) {
		this.storage();
		try {
			writer =new FileWriter(districtsFile.getAbsolutePath());
			writer.write("[");
			for(District district:districts) {
				JSONObject jsonDistrict = new JSONObject(district);
			writer.write(jsonDistrict.toString());
			writer.write("\n,");
			}
			writer.write("]");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<District> getDistricts(){
		this.storage();
		JSONArray jsonArray = null;
		ArrayList<District> districts = new ArrayList<>();
		ArrayList<Member> members = new ArrayList<>();
		try {
			String contents = new String((Files.readAllBytes(Paths.get(districtsFile.getAbsolutePath()))));
			jsonArray = new JSONArray(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			JSONObject jsonDelegate = jsonObj.getJSONObject("delegate");
			Member delegate = new Member(jsonDelegate.getInt("id"),jsonDelegate.getString("name"),Date.valueOf(jsonDelegate.getString("involved")),Grade.valueOf(jsonDelegate.getString("grade")));
			JSONArray jsonMembers= jsonObj.getJSONArray("members");
			for (int j =0; j<jsonMembers.length();j++) {
				JSONObject jsonMember = jsonMembers.getJSONObject(j);
				Member member = new Member(jsonMember.getInt("id"),
											jsonMember.getString("name"),
											Date.valueOf(jsonMember.getString("involved")),
											Grade.valueOf(jsonMember.getString("grade")));
				members.add(member);
			}
			District district = new District(jsonObj.getInt("id"),
									   jsonObj.getString("name"),
									   Date.valueOf(jsonObj.getString("created")),
									   delegate,
									   members);
			districts.add(district);
		}
		return districts;
	}
	public District getDistrictById(Integer id) {
		List<District> districts = getDistricts().stream().filter(m->m.getId()==id).toList();
		if(districts.size()==0) {
			return null;
		}
		return districts.get(0);
	}
	public boolean updateDistrict(Integer id, District district) {
		District newDistrict = getDistrictById(id);
		if(newDistrict!=null) {
			newDistrict.setName(district.getName());
			newDistrict.setCreated(district.getCreated());
			newDistrict.setDelegate(district.getDelegate());
			newDistrict.setMembers(district.getMembers());
			List<District> districts = getDistricts();
			districts.removeIf(d->d.getId()==id);
			storeDistricts(districts);
			storeDistrict(newDistrict);
			return true;
		}
		return false;
	}
	public boolean deleteDistrict(Integer id) {
		List<District> districts = getDistricts();
		boolean action = districts.removeIf(m->m.getId()==id);
		storeDistricts(districts);
		return action;
	}
}
