package com.example.ecommerce.network;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartViewResponse;
import com.example.ecommerce.model.LoginModel;
import com.example.ecommerce.model.LoginResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Order1;
import com.example.ecommerce.model.OrderResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductList;
import com.example.ecommerce.model.RegisterModel;
import com.example.ecommerce.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface apiInterface {
    //@FormUrlEncoded
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    //@POST("/auth/login/")
    /** auth urls**/
    @POST("/auth/login/")
    Call<LoginResponse> getLoginUser(@Body LoginModel user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/auth/register/")
    Call<RegisterResponse> createUser(@Body RegisterModel registerModel);


    /** product urls **/
    @GET("/products/")
    Call<ProductList> getAllProducts();

    @GET("/products/{slug}/")
    Call<Product> getProductDetail(@Path("slug") String slug);

    @GET("/products/{id}/")
    Call<Product> getProductDetail(@Path("id") int id);


    /** cart urls **/
    @FormUrlEncoded
    @POST("/orders/cart/add/")
    Call<Cart> addProductToCart(@Field("cart") String user, @Field("quantity") int quantity, @Field("item") int item);

    @GET("/orders/cart/view/{cart}/")
    Call<CartViewResponse> getCartView(@Path("cart") int cart_id);

    @DELETE("/orders/cart/delete/{id}")
    Call<Response> deleteItem(@Path("id") int item_id);

    @FormUrlEncoded
    @PATCH("/orders/cart/update/{id}/")
    Call<Cart> updateCartItem( @Path("id") int id, @Field("quantity") int qty);

    /** OrderUrls**/

    @GET("/orders/order/{id}/")
    Call<OrderResponse> getAllOrders(@Path("id") int id);

    @POST("/orders/order/{user}/")
    Call<Order> placeOrder(@Path("user") int id,@Body Order1 mOrder);



}

