package com.collar.named.service;

import com.collar.named.dao.SancaiDao;
import com.collar.named.entity.Sancai;
import com.collar.named.entity.Wuge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Frank on 7/21/16.
 */
@Service("sancaiService")
public class SancaiService {

    @Autowired
    @Qualifier("sancaiDao")
    private SancaiDao sancaiDao;

    @Autowired
    @Qualifier("wugeService")
    private WugeService wugeService;

    public Sancai getSancai(String name, boolean isDCF){
        Wuge tiange = wugeService.getTianGe(name, isDCF);
        Wuge dige = wugeService.getDiGe(name, isDCF);
        Wuge renge = wugeService.getRenGe(name, isDCF);
        return getSancai(tiange,dige,renge);
    }

    public Sancai getSancai(Wuge tiange , Wuge dige, Wuge renge){
        if (tiange == null || dige == null || renge == null) {
            return null;
        }
        int tianCai = tiange.getNumber() % 10;
        int diCai = dige.getNumber() % 10;
        int renCai = renge.getNumber() % 10;
        // 天人地
        String sancaiName = getSanCaiWuxing(tianCai) + getSanCaiWuxing(renCai) + getSanCaiWuxing(diCai);
        return sancaiDao.getSancai(sancaiName);
    }

    private String getSanCaiWuxing(int num) {
        switch (num) {
            case 1:
            case 2:
                return "木";
            case 3:
            case 4:
                return "火";
            case 5:
            case 6:
                return "土";
            case 7:
            case 8:
                return "金";
            case 9:
            case 0:
                return "水";
            default:
                return "";
        }
    }
}



