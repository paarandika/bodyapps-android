package fossasia.valentina.bodyapp.models;

import java.io.Serializable;

/**
 * Model object for measurement
 */
public class Measurement implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ID;
	private String userID;
	private int personID;
	private String created;
	private String lastSync;
	private String lastEdit;
	private int unit;
	private String mid_neck_girth;
	private String bust_girth;
	private String waist_girth;
	private String hip_girth;
	private String across_back_shoulder_width;
	private String shoulder_drop;
	private String shoulder_slope_degrees;
	private String arm_length;
	private String upper_arm_girth;
	private String armscye_girth;
	private String height;
	private String hip_height;
	private String wrist_girth;

	public Measurement(String iD, String userID, int personID, int unit) {
		super();
		ID = iD;
		this.userID = userID;
		this.personID = personID;
		this.unit = unit;
		this.mid_neck_girth = "";
		this.bust_girth = "";
		this.waist_girth = "";
		this.hip_girth = "";
		this.across_back_shoulder_width = "";
		this.shoulder_drop = "";
		this.shoulder_slope_degrees = "";
		this.arm_length = "";
		this.upper_arm_girth = "";
		this.armscye_girth = "";
		this.height = "";
		this.hip_height = "";
		this.wrist_girth = "";
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastSync() {
		return lastSync;
	}

	public void setLastSync(String lastSync) {
		this.lastSync = lastSync;
	}

	public String getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(String lastEdit) {
		this.lastEdit = lastEdit;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getMid_neck_girth() {
		return mid_neck_girth;
	}

	public void setMid_neck_girth(String mid_neck_girth) {
		this.mid_neck_girth = mid_neck_girth;
	}

	public String getBust_girth() {
		return bust_girth;
	}

	public void setBust_girth(String bust_girth) {
		this.bust_girth = bust_girth;
	}

	public String getWaist_girth() {
		return waist_girth;
	}

	public void setWaist_girth(String waist_girth) {
		this.waist_girth = waist_girth;
	}

	public String getHip_girth() {
		return hip_girth;
	}

	public void setHip_girth(String hip_girth) {
		this.hip_girth = hip_girth;
	}

	public String getAcross_back_shoulder_width() {
		return across_back_shoulder_width;
	}

	public void setAcross_back_shoulder_width(String across_back_shoulder_width) {
		this.across_back_shoulder_width = across_back_shoulder_width;
	}

	public String getShoulder_drop() {
		return shoulder_drop;
	}

	public void setShoulder_drop(String shoulder_drop) {
		this.shoulder_drop = shoulder_drop;
	}

	public String getShoulder_slope_degrees() {
		return shoulder_slope_degrees;
	}

	public void setShoulder_slope_degrees(String shoulder_slope_degrees) {
		this.shoulder_slope_degrees = shoulder_slope_degrees;
	}

	public String getArm_length() {
		return arm_length;
	}

	public void setArm_length(String arm_length) {
		this.arm_length = arm_length;
	}

	public String getUpper_arm_girth() {
		return upper_arm_girth;
	}

	public void setUpper_arm_girth(String upper_arm_girth) {
		this.upper_arm_girth = upper_arm_girth;
	}

	public String getArmscye_girth() {
		return armscye_girth;
	}

	public void setArmscye_girth(String armscye_girth) {
		this.armscye_girth = armscye_girth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHip_height() {
		return hip_height;
	}

	public void setHip_height(String hip_height) {
		this.hip_height = hip_height;
	}

	public String getWrist_girth() {
		return wrist_girth;
	}

	public void setWrist_girth(String wrist_girth) {
		this.wrist_girth = wrist_girth;
	}
}
