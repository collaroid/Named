package com.collar.named.api;

import com.collar.named.service.NamedService;
import com.collar.named.util.ResultResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Frank on 7/23/16.
 */

@Controller
@RequestMapping("/named")
public class NamedController {


    @Autowired
    @Qualifier("namedService")
    private NamedService namedService;

    @RequestMapping(value ="start.do",method = RequestMethod.GET)
    @ResponseBody
    public ResultResponse<ArrayList> spiderStart(HttpServletRequest request) throws Exception {
        ResultResponse<ArrayList> resultResponse = new ResultResponse<ArrayList>();
        long startTimestamp = System.currentTimeMillis();
        String familyName = StringUtils.trimToEmpty(request.getParameter("fn"));
        namedService.generateName(familyName);
        long endTimestamp = System.currentTimeMillis();
        resultResponse.setIsok(true);
        resultResponse.setMessage("spend time: " + (endTimestamp - startTimestamp)/1000);
        return resultResponse;
    }
}
