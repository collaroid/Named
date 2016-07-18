package com.collar.named.service;

import com.collar.named.dao.CharacterDao;
import com.collar.named.entity.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Frank on 7/19/16.
 */
@Service("characterService")
public class CharacterService {

    @Autowired
    @Qualifier("characterDao")
    private CharacterDao characterDao;

    public Character getCharacter(String c){
        return characterDao.getCharacter(c);
    }
}
