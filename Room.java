package validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
 
public class Room {
	private String roomNum;
	private String building;
	private String capacity;
	private String campus;
	private String maintenanceDay;
	private String startTime;
	private String duration;

	
//public Room(){
//}

/*
public Room(String information) {
	super();
	List alist = new ArrayList();
	StringTokenizer st = new StringTokenizer(information, "|");
	while(st.hasMoreTokens()){
		alist.add(st.nextToken());
	}
	this.setRoomNum(alist.get(0));
	this.setBuilding(alist.get(1));
	this.setCapacity(alist.get(2));
	this.setCampus(alist.get(3));
	this.setMaintenanceDay(alist.get(4));
	this.setStartTime(alist.get(5));
	this.setDuration(alist.get(6));
}
 */
	
public Room(String roomNum, String building, String capacity, String campus, String maintenanceDay, String startTime, String duration) {
	setRoomNum(roomNum);
	setBuilding(building);
	setCapacity(capacity);
	setCampus(campus);
	setMaintenanceDay(maintenanceDay);
	setStartTime(startTime);
	setDuration(duration);
}
 



public void setRoomNum(String roomNum){
	this.roomNum = roomNum;
}
public String getRoomNum(){
	return roomNum;
}
public void setBuilding(String building){
	this.building = building;
}
public String getBuilding() {
	return building;
}
public void setCapacity(String capacity) {
	this.capacity = capacity;
}
public String getCapacity() {
	return capacity;
}
public void setCampus(String campus) {
	this.campus = campus;
}
public String getCampus() {
	return campus;
}
public void setMaintenanceDay(String maintenanceDay) {
	this.maintenanceDay = maintenanceDay;
}
public String getMaintenanceDay() {
	return maintenanceDay;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getStartTime() {
	return startTime;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getDuration() {
	return duration;
}
 
}
 