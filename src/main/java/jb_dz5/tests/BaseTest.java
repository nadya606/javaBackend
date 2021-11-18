package jb_dz5.tests;

import com.github.javafaker.Faker;
import jb_dz5.Service.CategoryService;
import jb_dz5.Service.ProductService;
import jb_dz5.utils.RetrofitUtils;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;

public class BaseTest {
        static Retrofit retrofit;
        static CategoryService categoryService;
        static ProductService productService;
        static Faker faker;

        @BeforeAll
        static void beforeAll() {
            retrofit = RetrofitUtils.getRetrofit();
            categoryService = retrofit.create(CategoryService.class);
            productService = retrofit.create(ProductService.class);
            faker = new Faker();
        }
}

