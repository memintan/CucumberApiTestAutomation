package AgileProApi.pojo;

import com.google.gson.annotations.SerializedName;

public class Spartan {

    private Integer id;
    private String name;
    private String gender;
    @SerializedName("phone")
    private Integer mobilePhoneNumber;

    public Spartan(String name, String gender, Integer mobilePhoneNumber) {
        this.name = name;
        this.gender = gender;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobilePhoneNumber(Integer mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }


}
