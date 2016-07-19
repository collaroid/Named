package com.collar.named.service;

import com.collar.named.dao.WugeDao;
import com.collar.named.entity.Character;
import com.collar.named.entity.Wuge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Frank on 7/19/16.
 *
 * 什么是五格数理
 * 五格指的是姓名学中天格、地格、人格、总格、外格。
 * 五格剖象法是目前较有影响的一种取名法。
 * 最初在公元1918年初，由日本人熊崎健翁开创，因此五格剖象法也称作“熊崎氏姓名学”，其核心就是将人的姓名按五格剖象法来解释。
 * 所谓五格剖象法就是根据《易经》的“象”“数”理论，依据姓名的笔画数和一定的规律建立起的五格数理关系，并运用阴阳五行相生相克的道理，推算人生各 * 方面运势的一种简易方法。
 * 对于姓名学的内容，要用辩证的眼光去看，给后代、子女取名时不妨参考；
 * 如果自己目前的姓名数理配置不理想，也没有必要去改户口本上的名字，可以用笔名、常用名去补救
 * 原著上说，最常写、常用的名字对自己人生运势的暗示力最大。
 *
 * 五格数理计算方法：
 * 1、天格：复姓，合计姓氏之笔画；单姓，再加假添一数。如司马光，司马是复姓，天格是5+10=15；李刚，李是单姓，天格是7+1=8。天格乃祖先留下来的，其数理对人影响不大。
 * 2、人格，又称"主运"，是整个姓名的中心点，人一生的命运，均由此人格推断。其构成是姓氏（复姓的取复姓最后一字）与名字第一个字笔画数之和。如：刘江海，人格数是15+7=22。司马懿，人格数是10+22=32。
 * 3、地格：由名字全部笔画数构成，称为"前运"，主管人中年以前的活动力。如是单字名，再加假添一数。如司马懿，地格数是22+1=23。刘江海，地格数是7+11=18。
 * 4、总格：合计姓与名的总笔画数，主中年至晚年的命运，又称"后运"。如司马懿，总格数是5+10+22=37。刘江海，总格数是15+7+11=33。
 * 5、外格：总格笔画数减去人格笔画数，如是单字名或单姓，再加添一数。主管命运之灵力。如司马懿，外格数是37-32+1=6。刘江海，外格数是33-22+1=12（单姓的即最后一字笔画数+1）。
 *
 */
@Service("wugeService")
public class WugeService {

    @Autowired
    @Qualifier("characterService")
    private CharacterService characterService;

    @Autowired
    @Qualifier("wugeDao")
    private WugeDao wugeDao;

    /**
     * 根据名字返回天格
     * @param name 名字
     * @param isDCF 是否复姓
     * @return
     */
    public Wuge getTianGe(String name, boolean isDCF){
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (isDCF) {
            if (name.length() <= 2) {
                return null;
            }
            String name1 = name.substring(0, 1);
            String name2 = name.substring(1, 2);
            if (StringUtils.isBlank(name1) || StringUtils.isBlank(name2)) {
                return null;
            }
            Character character1 = characterService.getCharacter(name1);
            Character character2 = characterService.getCharacter(name2);
            if (character1 == null || character2 == null) {
                return null;
            }
            Wuge wuge = wugeDao.getWuge(character1.getStrokes() + character2.getStrokes());
            if (wuge == null) {
                return null;
            }
            System.out.println("'" + name + "'的天格是: " + wuge.getNumber());
            return wuge;
        } else {
            String name1 = name.substring(0, 1);
            if (StringUtils.isBlank(name1)) {
                return null;
            }
            Character character1 = characterService.getCharacter(name1);
            if (character1 == null) {
                return null;
            }
            Wuge wuge = wugeDao.getWuge(character1.getStrokes() + 1);
            if (wuge == null) {
                return null;
            }
            System.out.println("'" + name + "'的天格是: " + wuge.getNumber());
            return wuge;
        }
    }

    /**
     * 根据名字计算人格
     * @param name 名字
     * @param isDCF 是否复姓
     * @return
     */
    public Wuge getRenGe(String name, boolean isDCF){
        if (StringUtils.isBlank(name) || name.length() < 2
                || (name.length() < 3 && isDCF)) {
            return null;
        }
        String fn;
        String gn;
        if (isDCF) {
            fn = name.substring(1, 2);
            gn = name.substring(2, 3);
        } else {
            fn = name.substring(1, 2);
            gn = name.substring(2, 3);
        }
        Character character1 = characterService.getCharacter(fn);
        Character character2 = characterService.getCharacter(gn);
        if (character1 == null || character2 == null) {
            return null;
        }
        Wuge wuge = wugeDao.getWuge(character1.getStrokes() + character2.getStrokes());
        if (wuge == null) {
            return null;
        }
        System.out.println("'" + name + "'的人格是: " + wuge.getNumber());
        return wuge;
    }

    /**
     * 根据名字计算地格
     * @param name 名字
     * @param isDCF 是否复姓
     * @return
     */
    public Wuge getDiGe(String name, boolean isDCF){
        if (StringUtils.isBlank(name) || name.length() < 2
                || (name.length() < 3 && isDCF)) {
            return null;
        }
        int strokes = 0;
        char[] nameArray = name.toCharArray();
        int startIndex = isDCF ? 2 : 1;
        for (int i = startIndex; i < nameArray.length; i++) {
            Character character1 = characterService.getCharacter(String.valueOf(nameArray[i]));
            if (character1 == null) {
                continue;
            }
            strokes += character1.getStrokes();
        }
        if (strokes == 0) {
            return null;
        }
        if ((isDCF && name.length() == 3) || (!isDCF && name.length() == 2)) {
            strokes += 1;
        }
        Wuge wuge = wugeDao.getWuge(strokes);
        if (wuge == null) {
            return null;
        }
        System.out.println("'" + name + "'的地格是: " + wuge.getNumber());
        return wuge;
    }

    /**
     * 根据名字计算总格
     * @param name 名字
     * @return
     */
    public Wuge getZongGe(String name){
        if (StringUtils.isBlank(name)) {
            return null;
        }
        int strokes = 0;
        for (char c : name.toCharArray()) {
            Character character1 = characterService.getCharacter(String.valueOf(c));
            if (character1 == null) {
                continue;
            }
            strokes += character1.getStrokes();
        }
        if (strokes == 0) {
            return null;
        }
        Wuge wuge = wugeDao.getWuge(strokes);
        if (wuge == null) {
            return null;
        }
        System.out.println("'" + name + "'的总格是: " + wuge.getNumber());
        return wuge;
    }

    /**
     * 根据名字计算外格
     * @param name 名字
     * @param isDCF 是否复姓
     * @return
     */
    public Wuge getWaiGe(String name, boolean isDCF){
        if (StringUtils.isBlank(name) || name.length() < 2
                || (name.length() < 3 && isDCF)) {
            return null;
        }
        Wuge zongGe = getZongGe(name);
        Wuge renGe = getRenGe(name, isDCF);
        if (zongGe == null || renGe == null) {
            return null;
        }
        int strokes = zongGe.getNumber() - renGe.getNumber();
        if ((isDCF && name.length() == 3) || !isDCF) {
            strokes += 1;
        }
        Wuge wuge = wugeDao.getWuge(strokes);
        if (wuge == null) {
            return null;
        }
        System.out.println("'" + name + "'的外格是: " + wuge.getNumber());
        return wuge;
    }
}
