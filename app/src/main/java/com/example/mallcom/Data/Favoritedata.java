package com.example.mallcom.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Favoritedata {

    public class Category {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cat_img")
        @Expose
        private Object catImg;
        @SerializedName("sub_img")
        @Expose
        private Object subImg;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("subCategory")
        @Expose
        private String subCategory;
        @SerializedName("status")
        @Expose
        private Integer status;
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

        public Object getCatImg() {
            return catImg;
        }

        public void setCatImg(Object catImg) {
            this.catImg = catImg;
        }

        public Object getSubImg() {
            return subImg;
        }

        public void setSubImg(Object subImg) {
            this.subImg = subImg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("note")
        @Expose
        private Object note;
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("store_id")
        @Expose
        private Integer storeId;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("addetionalPrice")
        @Expose
        private String addetionalPrice;
        @SerializedName("offerText")
        @Expose
        private String offerText;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("final_price")
        @Expose
        private String finalPrice;
        @SerializedName("rate")
        @Expose
        private List<Rate> rate = null;
        @SerializedName("category")
        @Expose
        private Category category;
        @SerializedName("store")
        @Expose
        private Store store;
        @SerializedName("product_photos")
        @Expose
        private List<ProductPhoto> productPhotos = null;
        @SerializedName("additional_description")
        @Expose
        private Object additionalDescription;
        @SerializedName("product_sizes")
        @Expose
        private List<Object> productSizes = null;

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

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public String getAddetionalPrice() {
            return addetionalPrice;
        }

        public void setAddetionalPrice(String addetionalPrice) {
            this.addetionalPrice = addetionalPrice;
        }

        public String getOfferText() {
            return offerText;
        }

        public void setOfferText(String offerText) {
            this.offerText = offerText;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public String getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(String finalPrice) {
            this.finalPrice = finalPrice;
        }

        public List<Rate> getRate() {
            return rate;
        }

        public void setRate(List<Rate> rate) {
            this.rate = rate;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public List<ProductPhoto> getProductPhotos() {
            return productPhotos;
        }

        public void setProductPhotos(List<ProductPhoto> productPhotos) {
            this.productPhotos = productPhotos;
        }

        public Object getAdditionalDescription() {
            return additionalDescription;
        }

        public void setAdditionalDescription(Object additionalDescription) {
            this.additionalDescription = additionalDescription;
        }

        public List<Object> getProductSizes() {
            return productSizes;
        }

        public void setProductSizes(List<Object> productSizes) {
            this.productSizes = productSizes;
        }

    }

    public class Favoriteresponse {

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

    public class ProductPhoto {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("photo")
        @Expose
        private String photo;
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

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
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

    public class Rate {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("rate")
        @Expose
        private Integer rate;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
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

    }

    public class Store {

        @SerializedName("store_id")
        @Expose
        private Integer storeId;
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
        @SerializedName("store")
        @Expose
        private Store__1 store;

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
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

        public Store__1 getStore() {
            return store;
        }

        public void setStore(Store__1 store) {
            this.store = store;
        }

    }
    public class Store__1 {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("activity")
        @Expose
        private Integer activity;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

    }
}
