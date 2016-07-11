package com.collar.named.service;

import com.collar.named.entity.Character;
import com.collar.named.util.HttpUtils;

import java.util.regex.Pattern;

/**
 * Created by Frank on 7/10/16.
 */
public class Spider {

    private static String targetUrl = "http://www.t7y8.com/hanyu/kangxi/zidian/68.htm";

    private Character getCharacter(String url){
        Character character = new Character();
        String targetHtml = HttpUtils.httpGet(url, 10 * 1000);
        String c = targetHtml.substring(targetHtml.indexOf("『"), targetHtml.indexOf("』"));
        return character;
    }

    public static void main(String[] args) {
        String targetHtml = HttpUtils.httpGet(targetUrl, 10 * 1000);
        String c = targetHtml.substring(targetHtml.indexOf("『") + 1, targetHtml.indexOf("』"));
        System.out.println(c);
        

        Pattern urlPattern = Pattern.compile("^/hanyu/kangxi/bh/(\\d+)\\.htm$");
        String n = urlPattern.matcher(targetHtml).toString();
        System.out.println(n);
    }


}
