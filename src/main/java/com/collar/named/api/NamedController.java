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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value ="getNamePlus.do")
    public ModelAndView getNamePlus(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("firstname_list");
        String name = StringUtils.trimToEmpty(request.getParameter("name"));
        String borg = StringUtils.trimToEmpty(request.getParameter("borg"));
        List<String> nameList = namedService.getNamePlus(name, borg);
        mv.addObject("nameList", nameList);
        mv.addObject("borg", borg);
        return mv;
    }

    @RequestMapping(value ="getNameIndex.do")
    public ModelAndView getNameIndex(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("firstname_list");
        String familyname = StringUtils.trimToEmpty(request.getParameter("name"));
        String borg = StringUtils.trimToEmpty(request.getParameter("borg"));
        List<String> nameList = namedService.getNameIndex(familyname, borg);
        mv.addObject("nameList", nameList);
        mv.addObject("borg", borg);
        return mv;
    }
}
