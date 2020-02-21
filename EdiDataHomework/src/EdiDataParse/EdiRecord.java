package EdiDataParse;

// record parsed from csv file

public class EdiRecord {
	String userId; 
	String firstName; 
	String lastName; 
	Integer versionNo;
	String insCompany;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}
	public String getInsCompany() {
		return insCompany;
	}
	public void setInsCompany(String insCompany) {
		this.insCompany = insCompany;
	}
	
	// Override to dump record contents
	@Override
	  public String toString() {
	    String stringVal = "<userId:" + getUserId() + "><lastName:" + getLastName() + "><firstname:" +
	        getFirstName() + "><versionNo:" + getVersionNo() + "><insCompany:" + getInsCompany() + ">" ;
	    return stringVal;
	  }

}
