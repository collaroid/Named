package com.collar.named.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 7/23/16.
 */
@Repository("namedPoolDao")
public class NamedPoolDao extends BaseJdbcDao {

    public int insertName(String name, int tianId, int diId, int renId, int zongId, int waiId, int sancaiId, String borg){
        String sql="INSERT INTO `name_pool` (`name`, `tiange`, `dige`, `renge`, `waige`, `zongge`, `sancai`, `borg`) VALUES(:name,:tianId,:diId,:renId,:zongId,:waiId,:sancaiId,:borg)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("tianId", tianId);
        params.addValue("diId", diId);
        params.addValue("renId", renId);
        params.addValue("zongId", zongId);
        params.addValue("waiId", waiId);
        params.addValue("sancaiId", sancaiId);
        params.addValue("borg", borg);
        return this.namedJdbcTemplate.update(sql, params);
    }

    public List<String> getNamePlus (String name) {
        List<String> result = new ArrayList<>();
        String sql = "SELECT `name` FROM name_pool WHERE `name` LIKE '" + name + "%' AND CHAR_LENGTH(`name`) = " + (name.length() + 1);
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            result.add(rs.getString("name"));
        }
        return result;
    }
}
