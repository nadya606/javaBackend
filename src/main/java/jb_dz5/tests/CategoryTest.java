package jb_dz5.tests;

import com.github.javafaker.Faker;
import io.restassured.response.ResponseBody;
import jb_dz5.Service.CategoryService;
import jb_dz5.Service.ProductService;
import jb_dz5.dto.Product;
import jb_dz5.enums.CategoryType;
import jb_dz5.utils.PrettyLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import jb_dz5.dto.Category;
import retrofit2.Retrofit;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTest extends BaseTest {
    static Retrofit client;
    static CategoryService categoryService;
    Faker faker = new Faker();


        @Test
        void getCategoryId()throws IOException {
            Integer id = CategoryType.FOOD.getId();
            Response<Category> response = categoryService
                    .getCategory(id)
                    .execute();
            PrettyLogger.DEFAULT.log(response.toString());
            assertThat(response.body().getTitle(), equalTo(CategoryType.FOOD.getTitle()));
            assertThat(response.body().getId(), equalTo(id));
        }


}

