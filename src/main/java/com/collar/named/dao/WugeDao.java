package com.collar.named.dao;

import com.collar.named.entity.Wuge;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * Created by Frank on 7/14/16.
 */
@Repository("wugeDao")
public class WugeDao extends BaseJdbcDao {

    public Wuge getWuge(int number){
        String sql = "select * from `wuge` where `number` = :number";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("number", number);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql, params);
        Wuge wuge = null;
        if(rs.next()){
            wuge = new Wuge();
            wuge.setId(rs.getInt("id"));
            wuge.setNumber(rs.getInt("number"));
            wuge.setDes(rs.getString("des"));
            wuge.setAttr(rs.getString("attr"));
        }
        return wuge;
    }
}
