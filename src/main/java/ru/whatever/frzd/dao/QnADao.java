package ru.whatever.frzd.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.whatever.frzd.entity.QnA;

@Repository
public interface QnADao extends CrudRepository<QnA, Long> {

    List<QnA> findByQuestionContains(String partialQuestion);
}
