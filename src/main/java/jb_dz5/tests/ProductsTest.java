package jb_dz5.tests;

import com.github.javafaker.Faker;
import io.restassured.response.ResponseBody;
import jb_dz5.Service.CategoryService;
import jb_dz5.Service.ProductService;
import jb_dz5.dto.Category;
import jb_dz5.dto.Product;
import jb_dz5.enums.CategoryType;
import jb_dz5.utils.PrettyLogger;
import jb_dz5.utils.RetrofitUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.ArrayList;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductsTest {
    static Retrofit client;
    static ProductService productService;
    static CategoryService categoryService;
    Faker faker = new Faker();
    Product product;
    int productId;
    private int id;
    //PrettyLogger prettyLogger = new PrettyLogger();

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
        categoryService = client.create(CategoryService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() + 1) * 100)
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }

    @Test
    void getCategoryByIdTest()throws IOException {
        Integer id = CategoryType.FOOD.getId();
        Response<Category> response = categoryService
                .getCategory(id)
                .execute();
        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.body().getTitle(), equalTo(CategoryType.FOOD.getTitle()));
        assertThat(response.body().getId(), equalTo(id));
    }

    @Test
    void getProductsByIdTestTest()throws IOException {
        Response<Product> response = productService
                .getProduct(productId)
                .execute();
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
    }

    /*@Test
    void getProductsTest()throws IOException {
        Response <ArrayList<Product>> response = productService
                .getProducts()
                .execute();
        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
    }*/


    @Test
    void postProductTest() throws IOException {
        Response<Product> response = productService
                .createProduct(product)
                .execute();
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        productId = response.body().getId();
    }


   @AfterEach
    void returnData() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(productId).execute();
       assertThat("Delete", response.isSuccessful());
    }

}
