package ru.whatever.frzd.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.whatever.frzd.dto.QnADTO;
import ru.whatever.frzd.service.QnAService;
import ru.whatever.frzd.utils.NginxLogParser;

@RestController
@RequestMapping("main")
@CrossOrigin(origins = "*", maxAge = -1)
@RequiredArgsConstructor
public class Controller {

    private final QnAService service;

    private final Environment environment;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    List<QnADTO> check(@RequestBody(required = false) String partial) throws IOException {
        if (StringUtils.isEmpty(partial)) {
            log.info("Body is empty");
            return new ArrayList<>();
        }
        log.info(String.format("Request body: '%s'",partial));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(partial);

        // Extract the String value
        String question = jsonNode.get("question").asText();

        String context = environment.getProperty("application_context");
        List<QnADTO> resp = service.find(question.toLowerCase(), context);
        if (resp.size() == 0){
            QnADTO noDataDto = new QnADTO(String.format("По запросу '%s' нихрена не найдено", question), "Возможно стоит проверить орфографию, а так же исключить из поиска знаки препинания");
            List list = new ArrayList<>();
            list.add(noDataDto);
            log.info("Didn't find anything");
            return list;
        }
        log.info(String.format("Response is ready, %d elements",resp.size()));
        return resp;
    }

    @GetMapping("/stats")
    String getStats(){
        log.info("getting stats");
        return NginxLogParser.parseFile(environment.getProperty("nginx_log_file_path"));
    }
}
