package com.benson.aicodehelper.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LocalTimeToolTest {

    @Test
    void getLocalTimeDefaultsToSystemZone() {
        LocalTimeTool tool = new LocalTimeTool();
        LocalTimeTool.TimeInfo info = tool.getLocalTime(null);
        assertEquals("ok", info.status());
        assertNotNull(info.resolvedZoneId());
    }

    @Test
    void getLocalTimeHandlesInvalidZone() {
        LocalTimeTool tool = new LocalTimeTool();
        LocalTimeTool.TimeInfo info = tool.getLocalTime("Invalid/Zone");
        assertEquals("error", info.status());
        assertNotNull(info.message());
    }
}
