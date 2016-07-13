package com.collar.named.service;

import com.collar.named.entity.Attribute;
import com.collar.named.entity.Character;
import com.collar.named.util.HttpUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frank on 7/10/16.
 */
public class Spider {

    private String tmpUrl = "http://www.t7y8.com/hanyu/kangxi/zidian/@num.htm";

    private Character getCharacter(String url){
        Character character = new Character();
        String targetHtml = HttpUtils.httpGet(url, 10 * 1000);

        String c = targetHtml.substring(targetHtml.indexOf("『") + 1, targetHtml.indexOf("』"));

        Pattern numPattern = Pattern.compile("/hanyu/kangxi/bh/(\\d+)\\.htm");
        Matcher numMatcher = numPattern.matcher(targetHtml);
        String n = "笔画未取到";
        if (numMatcher.find()) {
           n = numMatcher.group(1);
        }

        Pattern wuPattern = Pattern.compile("<div class=\"tzg3\">([\\u4e00-\\u9fa5])");
        Matcher wuMatcher = wuPattern.matcher(targetHtml);
        String wu = "五行未取到";
        if (wuMatcher.find()) {
            wu = wuMatcher.group(1);
        }

        Pattern pingPattern = Pattern.compile("<div class=\"tzg3\">&nbsp;([A-Za-z]+)");
        Matcher pingMatcher = pingPattern.matcher(targetHtml);
        String ping = "拼音未取到";
        if (pingMatcher.find()) {
            ping = pingMatcher.group(1);
        }

        character.setValue(c);
        character.setAttribute(Attribute.getAttributeByName(wu));
        character.setPingying(ping);
        character.setUrl(url);
        try {
            character.setStrokes(Integer.valueOf(n));
        } catch (Exception e) {
            System.out.println(c + ": stroke not get");;
        }

        return character;
    }

    public void generateCharList(){
        ArrayList<Character> characterList = new ArrayList<Character>(7055);
        for (int i=1; i<=7055; i++){
            String url = tmpUrl.replace("@num", String.valueOf(i));
            Character character = getCharacter(url);
            characterList.add(character);
            System.out.println(character);
        }
        System.out.println(characterList.toString());
    }

    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.generateCharList();
    }
}
