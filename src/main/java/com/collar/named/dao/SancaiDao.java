package com.collar.named.dao;

import com.collar.named.entity.Sancai;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * Created by Frank on 7/21/16.
 */
@Repository("sancaiDao")
public class SancaiDao extends BaseJdbcDao {

    public Sancai getSancai(String name){
        String sql = "select * from `sancai` where `name` = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql, params);
        Sancai sancai = null;
        if(rs.next()){
            sancai = new Sancai();
            sancai.setId(rs.getInt("id"));
            sancai.setName(rs.getString("name"));
            sancai.setDes(rs.getString("des"));
            sancai.setAttr(rs.getString("attr"));
        }
        return sancai;
    }
}
