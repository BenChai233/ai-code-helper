package com.benson.aicodehelper.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesException;

@Slf4j
@Component("localTimeTool")
public class LocalTimeTool {

    @Tool(name = "local_time", value = "Get local time and timezone info. Optional timezoneId uses IANA IDs like 'Asia/Shanghai' or 'UTC'.")
    public TimeInfo getLocalTime(
            @P(value = "Optional IANA timezone ID. If empty, use system default.", required = false) String timezoneId
    ) {
        log.info("进入到localTimeTool");
        ZoneId zoneId;
        String requestedZone = (timezoneId == null || timezoneId.isBlank()) ? null : timezoneId.trim();
        try {
            zoneId = (requestedZone == null) ? ZoneId.systemDefault() : ZoneId.of(requestedZone, ZoneId.SHORT_IDS);
        } catch (ZoneRulesException | IllegalArgumentException ex) {
            return TimeInfo.error("Invalid timezoneId: " + requestedZone);
        }

        ZonedDateTime now = ZonedDateTime.now(zoneId);
        Instant instant = now.toInstant();
        return TimeInfo.ok(
                requestedZone,
                zoneId.getId(),
                now.getOffset().toString(),
                now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                instant.toString(),
                instant.toEpochMilli()
        );
    }

    public record TimeInfo(
            String status,
            String message,
            String requestedZoneId,
            String resolvedZoneId,
            String offset,
            String localDateTime,
            String isoInstant,
            long epochMillis
    ) {
        public static TimeInfo ok(String requestedZoneId,
                                  String resolvedZoneId,
                                  String offset,
                                  String localDateTime,
                                  String isoInstant,
                                  long epochMillis) {
            return new TimeInfo("ok", null, requestedZoneId, resolvedZoneId, offset, localDateTime, isoInstant, epochMillis);
        }

        public static TimeInfo error(String message) {
            return new TimeInfo("error", message, null, null, null, null, null, 0L);
        }
    }
}
