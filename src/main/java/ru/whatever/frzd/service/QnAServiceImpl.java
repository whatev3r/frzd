package ru.whatever.frzd.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.whatever.frzd.dao.QnADao;
import ru.whatever.frzd.dto.QnADTO;
import ru.whatever.frzd.entity.QnA;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnADao dao;

    @Override
    public List<QnADTO> find(String partialQuestion) {
        return dao.findByQuestionContains(partialQuestion).stream().map(QnADTO::new).collect(Collectors.toList());
    }

    @Override
    public QnA saveOrUpdate(QnA entity) {
        return null;
    }

    @Override
    public QnA find(Long aLong) {
        return null;
    }
}
