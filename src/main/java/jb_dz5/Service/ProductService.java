package jb_dz5.Service;

import io.restassured.response.ResponseBody;
import jb_dz5.dto.Category;
import jb_dz5.dto.Product;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ProductService {
    @GET("category") //сделано в классе CategoryTest
    Call<Category> getCategory(@Path("id") int id); // Get категории продуктов по id

    @GET("products") // не удалось сделать в классе ProductTest
    Call<ArrayList<Product>> getProducts(); // Get списка продуктов

    @POST("products") //сделано в классе ProductTest
    Call<Product> createProduct(@Body Product product); // POST создание продукта

    @PUT("products") //Сделано в классе UpdateTest
    Call<Product> updateProduct(@Body Product Product); // PUT изменение продукта

    @GET("products/{id}") //сделано в классе ProductTest
    Call<Product> getProduct(@Path("id") int id); // Get продукта по id

    @DELETE("products/{id}") //сделано в классе DeleteTest
    Call<ResponseBody> deleteProduct(@Path("id") Integer id); // Delete удаление продукта по id
}
