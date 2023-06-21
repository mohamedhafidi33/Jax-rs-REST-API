package ma.shm.hassani.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ma.shm.hassani.entities.District;
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
	
	public void storeDistrict(District district ) {
		this.storage();
	JSONObject jsonDistrict = new JSONObject(district);
	try {
		writer =new FileWriter(districtsFile.getAbsolutePath());
		writer.write(jsonDistrict.toString());
		writer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	public ArrayList<District> getMembers(){
		this.storage();
		JSONArray jsonArray = null;
		ArrayList<District> districts = new ArrayList<>();
		try {
			String contents = new String((Files.readAllBytes(Paths.get(districtsFile.getAbsolutePath()))));
			jsonArray = new JSONArray(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			District district = new District(jsonObj.getInt("id"),
									   jsonObj.getString("name"),
									   Date.valueOf(jsonObj.getString("created")),
									  (Member) JSONObject.stringToValue(jsonObj.getJSONObject("delegate").toString()),
									   (ArrayList<Member>) JSONObject.stringToValue(jsonObj.getJSONArray("members").toString()));
			districts.add(district);
		}
		return districts;
	}
	public District getById(Integer id) {
		return this.getMembers().stream().filter(m->m.getId()==id).toList().get(0);
	}
}
