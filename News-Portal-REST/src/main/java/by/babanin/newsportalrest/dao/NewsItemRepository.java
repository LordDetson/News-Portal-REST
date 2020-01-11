package by.babanin.newsportalrest.dao;

import by.babanin.newsportalrest.model.NewsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "news", path = "news")
public interface NewsItemRepository extends PagingAndSortingRepository<NewsItem, Integer> {
    @Override
    List<NewsItem> findAll();

    @RestResource(path = "titleIsContaining", rel = "titleIsContaining")
    Page<NewsItem> findAllByTitleIsContaining(@Param("titlePattern") String title, Pageable pageable);
}
