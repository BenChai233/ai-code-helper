package com.benson.aicodehelper.controller;

import com.benson.aicodehelper.service.AiCodeHelperService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AiCodeHelperController {
    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @GetMapping("/chat/{cid}")
    public String chat(@PathVariable String cid,
                       @RequestParam("msg") String msg) {
        String response = aiCodeHelperService.chatWithMemory(cid, msg);
        System.out.println(response);
        return response;
    }

}
