package com.collar.named.service;

import com.collar.named.dao.NamedPoolDao;
import com.collar.named.entity.Character;
import com.collar.named.entity.Sancai;
import com.collar.named.entity.Wuge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Character> characterArrayList = characterService.getCharacterArrayList();
        if (characterArrayList == null || characterArrayList.size() == 0){
            System.out.println("generate name failed cause character list is empty!");
            return;
        }
        boolean isDCF = familyName.length() == 2;
        // 单字名
        for (Character character : characterArrayList) {
            String fullName1 = familyName + character.getKey();
            isNameCanUse(fullName1, isDCF);
            // 双字名
            for (Character character2 : characterArrayList) {
                String fullName2 = familyName + character.getKey() + character2.getKey();
                isNameCanUse(fullName2, isDCF);
            }
        }
    }

    private void isNameCanUse (final String fullName, final boolean isDCF) {

        taskExecutor.execute(new Runnable() {
            public void run() {
                Wuge tiange = wugeService.getTianGe(fullName, isDCF);
                Wuge dige = wugeService.getDiGe(fullName, isDCF);
                if (!level.equals(dige.getAttr())) {
                    System.out.println(fullName + ": 地格不满足要求!");
                    return;
                }
                Wuge renge = wugeService.getRenGe(fullName, isDCF);
                if (!level.equals(renge.getAttr())) {
                    System.out.println(fullName + ": 人格不满足要求!");
                    return;
                }
                Wuge zongge = wugeService.getZongGe(fullName);
                if (!level.equals(zongge.getAttr())) {
                    System.out.println(fullName + ": 总格不满足要求!");
                    return;
                }
                Wuge waige = wugeService.getWaiGe(fullName, isDCF);
                if (!level.equals(waige.getAttr())) {
                    System.out.println(fullName + ": 外格不满足要求!");
                    return;
                }
                Sancai sancai = sancaiService.getSancai(tiange, dige, renge);
                if (!level.equals(sancai.getAttr())) {
                    System.out.println(fullName + ": 三才不满足要求!");
                    return;
                }
                int row = namedPoolDao.insertName(fullName, tiange.getId(), dige.getId(), renge.getId(),zongge.getId(), waige.getId(),sancai.getId());
                if (row == 1) {
                    System.out.println(fullName + "匹配成功,存入db。");
                    return;
                }
                System.out.println(fullName + "匹配成功,存入db失败。");
                return;
            }
        });
    }
}
