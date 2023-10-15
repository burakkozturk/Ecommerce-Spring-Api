package emekpazari.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long userId;
    private Long categoryId;


    private String title;
    private String description;
    private double price;
    private String color;
    private int status;
    private String photoUrl;
}
