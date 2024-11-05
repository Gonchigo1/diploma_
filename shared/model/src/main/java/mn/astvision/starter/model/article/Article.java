package mn.astvision.starter.model.article;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.auth.User;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.article.enums.ArticleStatus;

@Sharded(shardKey = { "id", "categoryId" })
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntityWithUser {
    @NotBlank
    private String categoryId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String body;
    @Default
    private ArticleStatus status = ArticleStatus.DRAFT;
    private LocalDateTime publishedDate;
    @NotBlank
    private String mainImgUrl;
    private String thumbImgUrl;
    private boolean featured;

    @Transient
    private ArticleCategory category;

    @Transient
    private User publisher;
}
