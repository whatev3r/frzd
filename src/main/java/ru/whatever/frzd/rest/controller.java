package ru.whatever.frzd.rest;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.whatever.frzd.dto.QnADTO;
import ru.whatever.frzd.service.QnAService;

@RestController
@RequestMapping("main")
@CrossOrigin(origins = "*", maxAge = -1)
@RequiredArgsConstructor
public class controller {

    private final QnAService service;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    List<QnADTO> check(@RequestBody(required = false) String partial) {
        if (StringUtils.isEmpty(partial)) {
            log.info("Body is empty");
            return new ArrayList<>();
        }
        log.info(String.format("Request body: '%s'",partial));
        List<QnADTO> resp = service.find(partial.toLowerCase());
        if (resp.size() == 0){
            QnADTO noDataDto = new QnADTO(String.format("По запросу '%s' нихрена не найдено", partial), "Возможно стоит проверить орфографию, а так же исключить из поиска знаки препинания");
            List list = new ArrayList<>();
            list.add(noDataDto);
            log.info("Didn't find anything");
            return list;
        }
        log.info(String.format("Response is ready, %d elements",resp.size()));
        return resp;
    }

}
