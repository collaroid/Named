package com.collar.named.service;

import com.collar.named.dao.CharacterDao;
import com.collar.named.entity.Character;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Frank on 7/19/16.
 */
@Service("characterService")
public class CharacterService implements InitializingBean {

    @Autowired
    @Qualifier("characterDao")
    private CharacterDao characterDao;

    private static ArrayList<Character> characterArrayList = new ArrayList<Character>(7100);

    public Character getCharacter(String c){
        return characterDao.getCharacter(c);
    }

    public List<Character> getCharacterArrayList (){
        if (characterArrayList != null && characterArrayList.size() != 0) {
            return Collections.unmodifiableList(characterArrayList);
        }
        return null;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("charactor list init start !");
        characterArrayList.clear();
        characterArrayList.addAll(characterDao.getCharacterList());
        System.out.println("charactor list init end !");
    }
}
