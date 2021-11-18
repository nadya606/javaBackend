package jb_dz5.dto;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Category {
    Integer id;
    String title;
    ArrayList<Product> products = new ArrayList<>();
}
