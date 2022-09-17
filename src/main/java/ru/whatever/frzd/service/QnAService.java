package ru.whatever.frzd.service;

import java.util.List;
import ru.whatever.frzd.dto.QnADTO;
import ru.whatever.frzd.entity.QnA;

public interface QnAService extends BaseService<QnA, Long> {

    List<QnADTO> find(String partialQuestion);
}
