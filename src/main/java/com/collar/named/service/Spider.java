package com.collar.named.service;

import com.collar.named.dao.CharacterDao;
import com.collar.named.entity.Attribute;
import com.collar.named.entity.Character;
import com.collar.named.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frank on 7/10/16.
 */
@Service("spider")
public class Spider {

    @Autowired
    @Qualifier("characterDao")
    private CharacterDao characterDao;

    @Autowired
    @Qualifier("taskExecutor_http")
    private ThreadPoolTaskExecutor taskExecutor;

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

        character.setKey(c);
        Attribute attribute = Attribute.getAttributeByName(wu);
        if (attribute == Attribute.Unknow) {
            System.out.println(c + ": " + wu);
        }
        character.setAttribute(attribute);
        character.setPingying(ping);
        character.setUrl(url);
        try {
            character.setStrokes(Integer.valueOf(n));
        } catch (Exception e) {
            System.out.println(c + ": stroke not get");;
        }

        return character;
    }

    private Vector<Character> generateCharList(){
        final Vector<Character> characterList = new Vector<Character>(7055);
        final CountDownLatch latch = new CountDownLatch(7055);
        for (int i=1; i<=7055; i++){
            final String url = tmpUrl.replace("@num", String.valueOf(i));
            final int id = i;
            taskExecutor.execute(new Runnable() {
                public void run() {
                    Character character = null;
                    try {
                        character = getCharacter(url);
                        character.setId(id);
                    } catch (Exception e) {
                        System.out.println("第" + id + "个异常:" + e.getMessage());
                        latch.countDown();
                        return;
                    }
                    characterList.add(character);
                    latch.countDown();
                    System.out.println("第" + id + "个:" + character);
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return characterList;
    }

    public boolean storeCharacter(){
        Vector<Character> characters = generateCharList();
        if (characters == null || characters.isEmpty()) {
            System.out.println("char list is empty!");
            return false;
        }
        int[] result = characterDao.insertCharacterList(characters);
        if (result == null || result.length == 0) {
            return false;
        }
        return true;
    }

    private Character getTestCharacter() {
        Character character = new Character();
        character.setUrl("test");
        character.setPingying("pingying");
        character.setId(1);
        character.setAttribute(Attribute.Earth);
        character.setStrokes(12);
        character.setKey("A");
        return character;
    }
}
