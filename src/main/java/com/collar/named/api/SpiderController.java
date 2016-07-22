package com.collar.named.api;

import com.collar.named.service.Spider;
import com.collar.named.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Frank on 7/10/16.
 */
@Controller
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    @Qualifier("spider")
    private Spider spider;

    @RequestMapping(value ="start.do",method = RequestMethod.GET)
    @ResponseBody
    public ResultResponse<ArrayList> spiderStart(HttpServletRequest request) throws Exception {
        ResultResponse<ArrayList> resultResponse = new ResultResponse<ArrayList>();
        long startTimestamp = System.currentTimeMillis();
        resultResponse.setIsok(spider.storeCharacter());
        long endTimestamp = System.currentTimeMillis();
        resultResponse.setMessage("spend time: " + (endTimestamp - startTimestamp)/1000);
        return resultResponse;
    }
}
