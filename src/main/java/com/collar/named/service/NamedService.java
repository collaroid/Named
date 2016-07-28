package com.collar.named.service;

import com.collar.named.dao.NamedPoolDao;
import com.collar.named.entity.Sancai;
import com.collar.named.entity.Wuge;
import com.collar.named.util.LoadConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by Frank on 7/23/16.
 */
@Service("namedService")
public class NamedService {

    @Autowired
    @Qualifier("characterService")
    private CharacterService characterService;

    @Autowired
    @Qualifier("wugeService")
    private WugeService wugeService;

    @Autowired
    @Qualifier("sancaiService")
    private SancaiService sancaiService;

    @Autowired
    @Qualifier("namedPoolDao")
    private NamedPoolDao namedPoolDao;


    @Autowired
    @Qualifier("taskExecutor_http")
    private ThreadPoolTaskExecutor taskExecutor;

    private static final String level = "吉";

    public void generateName(String familyName){
        if (StringUtils.isBlank(familyName) || familyName.length() > 2) {
            System.out.println("generate name failed cause family name wrong! ");
            return;
        }
        boolean isDCF = familyName.length() == 2;
        /* 7055字匹配,耗时太长
        List<Character> characterArrayList = characterService.getCharacterArrayList();
        if (characterArrayList == null || characterArrayList.size() == 0){
            System.out.println("generate name failed cause character list is empty!");
            return;
        }
        */
        storeName(LoadConfig.get("boy_character"), familyName, isDCF, "b");
        storeName(LoadConfig.get("girl_character"), familyName, isDCF, "g");

    }

    private void storeName(String characterString, String familyName, boolean isDCF, String borg){
        if (StringUtils.isNotBlank(characterString)) {
            // boy name
            char[] chars = characterString.toCharArray();
            // 单字名
            for (char character : chars) {
                String fullName1 = familyName + character;
                isNameCanUse(fullName1, isDCF, borg);
                // 双字名
                for (char character2 : chars) {
                    String fullName2 = familyName + character + character2;
                    isNameCanUse(fullName2, isDCF, borg);
                }
            }
        }
    }

    private void isNameCanUse (String fullName, boolean isDCF, String borg) {
        Wuge tiange = wugeService.getTianGe(fullName, isDCF);
        Wuge dige = wugeService.getDiGe(fullName, isDCF);
        if (dige == null || !level.equals(dige.getAttr())) {
            System.out.println(fullName + ": 地格不满足要求!");
            return;
        }
        Wuge renge = wugeService.getRenGe(fullName, isDCF);
        if (renge == null || !level.equals(renge.getAttr())) {
            System.out.println(fullName + ": 人格不满足要求!");
            return;
        }
        Wuge zongge = wugeService.getZongGe(fullName);
        if (zongge == null || !level.equals(zongge.getAttr())) {
            System.out.println(fullName + ": 总格不满足要求!");
            return;
        }
        Wuge waige = wugeService.getWaiGe(fullName, isDCF);
        if (waige == null || !level.equals(waige.getAttr())) {
            System.out.println(fullName + ": 外格不满足要求!");
            return;
        }
        Sancai sancai = sancaiService.getSancai(tiange, dige, renge);
        if (sancai == null || !level.equals(sancai.getAttr())) {
            System.out.println(fullName + ": 三才不满足要求!");
            return;
        }
        int row = namedPoolDao.insertName(fullName, tiange.getId(), dige.getId(), renge.getId(),zongge.getId(), waige.getId(),sancai.getId(), borg);
        if (row != 0) {
            System.out.println(fullName + "匹配成功,存入db:" + row);
            return;
        }
        System.out.println(fullName + "匹配成功,存入db失败。");
    }
}
