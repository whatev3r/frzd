package ru.whatever.frzd.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NginxLogParser {

    public static String parseFile(String fileName) {
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> ips = new ArrayList<>();
        ArrayList<String> uuids = new ArrayList<>();

        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                line = line.replace("\n", "");
                String chr = "";
                String ip = "";
                String date = "";
                String mode = "IP";
                if (line.isEmpty()) {
                    continue;
                }
                if (!line.contains(" - - [")) {
                    continue;
                }
                if (!(line.contains("GET /favicon.ico HTTP/1.1\" 200") || line.contains(
                    "GET /favicon.ico HTTP/1.1\" 304"))) {
                    continue;
                }
                for (int i = 0; i < line.length(); i++) {
                    chr = line.substring(i, i + 1);
                    if (chr.equals(" ")) {
                        i += 5;
                        mode = "DATE";
                        continue;
                    }
                    if (chr.equals(":")) {
                        break;
                    }
                    if (mode.equals("IP")) {
                        ip = ip.concat(chr);
                    }
                    if (mode.equals("DATE")) {
                        date = date.concat(chr);
                    }
                }
                String uuid = line.split("\"")[5];
                DateTimeFormatter fuckedUpFormatter = DateTimeFormatter.ofPattern("dd/LLL/yyyy", Locale.ENGLISH);
                date = LocalDate.parse(date, fuckedUpFormatter).toString();

                addUnique(dates, date);
                addUnique(uuids, String.format("%s : %s", date, uuid));
                addUnique(ips, String.format("%s : %s", date, ip));
            }
            br.close();

            result = dates.stream().map(date -> {
                int counterIp = 0;
                int counterUuid = 0;
                for (String key : ips) {
                    if (key.startsWith(date + " : ")) {
                        counterIp = counterIp + 1;
                    }
                }

                for (String key : uuids) {
                    if (key.startsWith(date + " : ")) {
                        counterUuid = counterUuid + 1;
                    }
                }
                return date + " : от " + counterUuid + " до " + counterIp;
            }).collect(Collectors.joining("<br>"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private static void addUnique(ArrayList list, Object value) {
        if (!list.contains(value)) {
            list.add(value);
        }
    }

}
