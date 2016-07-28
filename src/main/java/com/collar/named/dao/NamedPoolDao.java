package com.collar.named.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Created by Frank on 7/23/16.
 */
@Repository("namedPoolDao")
public class NamedPoolDao extends BaseJdbcDao {

    public int insertName(String name, int tianId, int diId, int renId, int zongId, int waiId, int sancaiId, String borg){
        final String sql="INSERT INTO `name_pool` (`name`, `tiange`, `dige`, `renge`, `waige`, `zongge`, `sancai`, `borg`) VALUES(:name,:tianId,:diId,:renId,:zongId,:waiId,:sancaiId,:borg)";
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
}
