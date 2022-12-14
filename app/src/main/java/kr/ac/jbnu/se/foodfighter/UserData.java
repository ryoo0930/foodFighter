package kr.ac.jbnu.se.foodfighter;

public class UserData {
    private String personFamilyName;
    private String personGivenName;
    private String personId;

    public UserData() {
    }

    public UserData(String personFamilyName, String personGivenName, String personId) {
        this.personFamilyName = personFamilyName;
        this.personGivenName = personGivenName;
        this.personId = personId;
    }

    public void setPersonFamilyName(String personFamilyName) {
        this.personFamilyName = personFamilyName;
    }

    public String getPersonFamilyName() {
        return personFamilyName;
    }

    public void setPersonGivenName(String personGivenName) {
        this.personGivenName = personGivenName;
    }

    public String getpersonGivenName() {
        return personGivenName;
    }

    public void setpersonId(String personId) {
        this.personId = personId;
    }

    public String getpersonId() {
        return personId;
    }

}
