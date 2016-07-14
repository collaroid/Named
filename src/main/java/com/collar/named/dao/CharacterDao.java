package com.collar.named.dao;

import com.collar.named.entity.Character;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Frank on 7/14/16.
 */
@Repository("characterDao")
public class CharacterDao extends BaseJdbcDao {

    public int[] insertCharacterList (final List<Character> characterList) {
        final String sql = "INSERT INTO character(key, attribute, strokes, pingying, url) VALUES(?,?,?,?) ";

        return this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Character character = characterList.get(i);
                ps.setString(1, character.getKey());
                ps.setInt(2, character.getAttribute().getId());
                ps.setInt(3, character.getStrokes());
                ps.setString(4, character.getPingying());
                ps.setString(5, character.getUrl());
            }

            public int getBatchSize() {
                return characterList.size();
            }
        });
    }
}
