package com.collar.named.service;

import com.collar.named.entity.Character;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by Frank on 7/23/16.
 */
public class NamedService {

    @Autowired
    @Qualifier("characterService")
    private CharacterService characterService;

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



    }
}
