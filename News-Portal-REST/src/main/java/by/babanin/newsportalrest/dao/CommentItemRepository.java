package by.babanin.newsportalrest.dao;

import by.babanin.newsportalrest.model.CommentItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentItemRepository extends PagingAndSortingRepository<CommentItem, Integer> {
    @Override
    List<CommentItem> findAll();
}
