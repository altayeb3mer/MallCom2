package com.example.mallcom.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profiledata {

    public class Account {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cardName")
        @Expose
        private String cardName;
        @SerializedName("cardNumber")
        @Expose
        private Long cardNumber;
        @SerializedName("expireDate")
        @Expose
        private String expireDate;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public Long getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(Long cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("birthDate")
        @Expose
        private String birthDate;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("activity")
        @Expose
        private Integer activity;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("account")
        @Expose
        private List<Account> account = null;
        @SerializedName("state")
        @Expose
        private State state;
        @SerializedName("role")
        @Expose
        private Role role;
        @SerializedName("favourit")
        @Expose
        private List<Favourit> favourit = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getActivity() {
            return activity;
        }

        public void setActivity(Integer activity) {
            this.activity = activity;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Account> getAccount() {
            return account;
        }

        public void setAccount(List<Account> account) {
            this.account = account;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public List<Favourit> getFavourit() {
            return favourit;
        }

        public void setFavourit(List<Favourit> favourit) {
            this.favourit = favourit;
        }

    }

    public class Favourit {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

    public class Profileresponse {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("error")
        @Expose
        private Object error;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public class Role {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("roleCode")
        @Expose
        private Integer roleCode;
        @SerializedName("position")
        @Expose
        private String position;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(Integer roleCode) {
            this.roleCode = roleCode;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

    }

    public class State {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("deliverTime")
        @Expose
        private Integer deliverTime;
        @SerializedName("deliverPrice")
        @Expose
        private Integer deliverPrice;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getDeliverTime() {
            return deliverTime;
        }

        public void setDeliverTime(Integer deliverTime) {
            this.deliverTime = deliverTime;
        }

        public Integer getDeliverPrice() {
            return deliverPrice;
        }

        public void setDeliverPrice(Integer deliverPrice) {
            this.deliverPrice = deliverPrice;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
