package fossasia.valentina.bodyapp.models;

import java.io.Serializable;
import java.util.Date;



public class Measurement implements Serializable{
	private String ID;
	private String userID;
	private int personID;
	private Date created;
	private Date lastSync;
	private Date lastEdit;
	private int unit;
	private int mid_neck_girth;
	private int bust_girth;
	private int waist_girth;
	private int hip_girth;
	private int across_back_shoulder_width;
	private int shoulder_drop;
	private int shoulder_slope_degrees;
	private int arm_length;
	private int upper_arm_girth;
	private int armscye_girth;
	private int height;
	private int hip_height;
	
	public Measurement(String ID, String userID) {
		super();
		this.ID = ID;
		this.userID=userID;
	}

	public Measurement(String iD, String userID, int personID, Date created,
			Date lastSync, Date lastEdit, int unit, int mid_neck_girth,
			int bust_girth, int waist_girth, int hip_girth,
			int across_back_shoulder_width, int shoulder_drop,
			int shoulder_slope_degrees, int arm_length, int upper_arm_girth,
			int armscye_girth, int height, int hip_height) {
		super();
		ID = iD;
		this.userID = userID;
		this.personID = personID;
		this.created = created;
		this.lastSync = lastSync;
		this.lastEdit = lastEdit;
		this.unit = unit;
		this.mid_neck_girth = mid_neck_girth;
		this.bust_girth = bust_girth;
		this.waist_girth = waist_girth;
		this.hip_girth = hip_girth;
		this.across_back_shoulder_width = across_back_shoulder_width;
		this.shoulder_drop = shoulder_drop;
		this.shoulder_slope_degrees = shoulder_slope_degrees;
		this.arm_length = arm_length;
		this.upper_arm_girth = upper_arm_girth;
		this.armscye_girth = armscye_girth;
		this.height = height;
		this.hip_height = hip_height;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastSync() {
		return lastSync;
	}

	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
	}

	public Date getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getMid_neck_girth() {
		return mid_neck_girth;
	}

	public void setMid_neck_girth(int mid_neck_girth) {
		this.mid_neck_girth = mid_neck_girth;
	}

	public int getBust_girth() {
		return bust_girth;
	}

	public void setBust_girth(int bust_girth) {
		this.bust_girth = bust_girth;
	}

	public int getWaist_girth() {
		return waist_girth;
	}

	public void setWaist_girth(int waist_girth) {
		this.waist_girth = waist_girth;
	}

	public int getHip_girth() {
		return hip_girth;
	}

	public void setHip_girth(int hip_girth) {
		this.hip_girth = hip_girth;
	}

	public int getAcross_back_shoulder_width() {
		return across_back_shoulder_width;
	}

	public void setAcross_back_shoulder_width(int across_back_shoulder_width) {
		this.across_back_shoulder_width = across_back_shoulder_width;
	}

	public int getShoulder_drop() {
		return shoulder_drop;
	}

	public void setShoulder_drop(int shoulder_drop) {
		this.shoulder_drop = shoulder_drop;
	}

	public int getShoulder_slope_degrees() {
		return shoulder_slope_degrees;
	}

	public void setShoulder_slope_degrees(int shoulder_slope_degrees) {
		this.shoulder_slope_degrees = shoulder_slope_degrees;
	}

	public int getArm_length() {
		return arm_length;
	}

	public void setArm_length(int arm_length) {
		this.arm_length = arm_length;
	}

	public int getUpper_arm_girth() {
		return upper_arm_girth;
	}

	public void setUpper_arm_girth(int upper_arm_girth) {
		this.upper_arm_girth = upper_arm_girth;
	}

	public int getArmscye_girth() {
		return armscye_girth;
	}

	public void setArmscye_girth(int armscye_girth) {
		this.armscye_girth = armscye_girth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHip_height() {
		return hip_height;
	}

	public void setHip_height(int hip_height) {
		this.hip_height = hip_height;
	}

	
	

}
