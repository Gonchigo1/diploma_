package mn.astvision.starter.dns;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dns.dto.ResourceRecord;
import mn.astvision.starter.dns.dto.ResourceRecordSet;
import mn.astvision.starter.dns.dto.ResourceRecordSets;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class DomainBindFileUtil {

    public static void convertJsonToBind(String filePath) {
        ObjectMapper om = new ObjectMapper();

        try {
            ResourceRecordSets resourceRecordSets = om.readValue(new File(filePath), ResourceRecordSets.class);

            StringBuilder sb = new StringBuilder();
            for (ResourceRecordSet resourceRecordSet : resourceRecordSets.getResourceRecordSets()) {
                switch (resourceRecordSet.getType()) {
                    case "A" -> {
                        if (resourceRecordSet.getAliasTarget() != null) {
                            // alias
                            if (!sb.isEmpty())
                                sb.append("\n");

                            sb
                                    .append(resourceRecordSet.getName()).append("\t")
                                    .append("300").append("\t")
                                    .append("CNAME").append("\t")
                                    .append(resourceRecordSet.getAliasTarget().getDnsName()).append("\t");
                        } if (!ObjectUtils.isEmpty(resourceRecordSet.getResourceRecords())) {
                            // энгийн record
                            for (ResourceRecord resourceRecord : resourceRecordSet.getResourceRecords()) {
                                if (!sb.isEmpty())
                                    sb.append("\n");

                                sb
                                        .append(resourceRecordSet.getName()).append("\t")
                                        .append("300").append("\t")
                                        .append("A").append("\t")
                                        .append(resourceRecord.getValue()).append("\t");
                            }
                        }
                    }
                    case "MX" -> {
                        if (!ObjectUtils.isEmpty(resourceRecordSet.getResourceRecords())) {
                            for (ResourceRecord resourceRecord : resourceRecordSet.getResourceRecords()) {
                                if (!sb.isEmpty())
                                    sb.append("\n");

                                sb
                                        .append(resourceRecordSet.getName()).append("\t")
                                        .append("300").append("\t")
                                        .append("IN").append("\t")
                                        .append("MX").append("\t")
                                        .append(resourceRecord.getValue()).append("\t");
                            }
                        }
                    }
                    case "CNAME", "TXT", "SRV" -> {
                        // энгийн CNAME, TXT, SRV record
                        for (ResourceRecord resourceRecord : resourceRecordSet.getResourceRecords()) {
                            if (!sb.isEmpty())
                                sb.append("\n");

                            sb
                                    .append(resourceRecordSet.getName()).append("\t")
                                    .append("300").append("\t")
                                    .append(resourceRecordSet.getType()).append("\t")
                                    .append(resourceRecord.getValue()).append("\t");
                        }
                    }
                    default -> log.warn("Unknown type: " + resourceRecordSet.getType());
                }
            }

            // write to bind file
            String outputPath = filePath + ".bind";
            Files.writeString(Paths.get(new File(outputPath).toURI()), sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
