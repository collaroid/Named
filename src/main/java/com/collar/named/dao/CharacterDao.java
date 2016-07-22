package com.collar.named.dao;

import com.collar.named.entity.Attribute;
import com.collar.named.entity.Character;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 7/14/16.
 */
@Repository("characterDao")
public class CharacterDao extends BaseJdbcDao {

    public int[] insertCharacterList (final List<Character> characterList) {
        final String sql = "INSERT INTO `character` (`key`, attribute, strokes, pingying, url, id) VALUES(?,?,?,?,?,?)";

        return this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Character character = characterList.get(i);
                ps.setString(1, character.getKey());
                ps.setInt(2, character.getAttribute().getId());
                ps.setInt(3, character.getStrokes());
                ps.setString(4, character.getPingying());
                ps.setString(5, character.getUrl());
                ps.setInt(6, character.getId());
            }

            public int getBatchSize() {
                return characterList.size();
            }
        });
    }

    public Character getCharacter(String c){
        String sql = "select * from `character` where `key` = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", c);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql, params);
        Character character = null;
        if(rs.next()){
            character = new Character();
            character.setId(rs.getInt("id"));
            character.setKey(rs.getString("key"));
            character.setStrokes(rs.getInt("strokes"));
            character.setAttribute(Attribute.getAttributeById(rs.getInt("attribute")));
            character.setUrl(rs.getString("url"));
            character.setPingying(rs.getString("pingying"));
        }
        return character;
    }

    public ArrayList<Character> getCharacterList () {
        String sql = "select * from `character`";
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        ArrayList<Character> characterArrayList = new ArrayList<Character>(7100);

        while (rs.next()){
            Character character = new Character();
            character.setId(rs.getInt("id"));
            character.setKey(rs.getString("key"));
            character.setStrokes(rs.getInt("strokes"));
            character.setAttribute(Attribute.getAttributeById(rs.getInt("attribute")));
            character.setUrl(rs.getString("url"));
            character.setPingying(rs.getString("pingying"));
            characterArrayList.add(character);
        }
        return characterArrayList;
    }
}
