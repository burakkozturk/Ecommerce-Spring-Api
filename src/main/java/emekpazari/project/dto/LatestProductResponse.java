package emekpazari.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestProductResponse {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String color;
    private String photoUrl;
    private String categoryName; // Eğer kategori adını da göndermek istiyorsanız

}
