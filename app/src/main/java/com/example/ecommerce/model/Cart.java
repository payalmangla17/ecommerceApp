package com.example.ecommerce.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("cart")
        @Expose
        private Integer cart;
        @SerializedName("item")
        @Expose
        private Integer item;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getCart() {
            return cart;
        }

        public void setCart(Integer cart) {
            this.cart = cart;
        }

        public Integer getItem() {
            return item;
        }

        public void setItem(Integer item) {
            this.item = item;
        }


}
