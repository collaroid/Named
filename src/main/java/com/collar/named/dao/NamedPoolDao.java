package com.collar.named.dao;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Frank on 7/23/16.
 */
@Repository("namedPoolDao")
public class NamedPoolDao extends BaseJdbcDao {

    public int insertName(final String name, final int tianId, final int diId, final int renId, final int zongId, final int waiId, final int sancaiId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql="INSERT INTO name_pool(`name`, tiange, dige, renge, waige, zongge, sancai) VALUES(?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[] { "id" });
                int i = 0;
                ps.setString(++i, name);
                ps.setInt(++i, tianId);
                ps.setInt(++i, diId);
                ps.setInt(++i, renId);
                ps.setInt(++i, waiId);
                ps.setInt(++i, zongId);
                ps.setInt(++i, sancaiId);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
