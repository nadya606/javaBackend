package jb_dz5.tests;

import com.github.javafaker.Faker;
import io.restassured.response.ResponseBody;
import jb_dz5.Service.ProductService;
import jb_dz5.dto.Product;
import jb_dz5.enums.CategoryType;
import jb_dz5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateTest {
    static Retrofit client;
    static ProductService productService;
    Faker faker = new Faker();
    Product product;
    int productId;
    private okhttp3.Response response;


    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() + 1) * 100)
                .withCategoryTitle(CategoryType.FOOD.getTitle());

        Response<Product> response = productService
                .createProduct(product)
                .execute();
        productId = response.body().getId();
        product.setId(productId);
        product.setPrice(666);
    }

    @Test
    void updateTest() {
        productService.updateProduct(product);
        assertThat("Updated", response.isSuccessful());
    }




}
