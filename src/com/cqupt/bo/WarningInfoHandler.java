package com.cqupt.bo;

import com.alibaba.fastjson.JSONObject;
import com.cqupt.dao.DriverInfoDao;
import com.cqupt.dao.WarningInfoDao;
import com.cqupt.entity.DriverInfo;
import com.cqupt.entity.WarningInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.List;

@Service("warningInfoHandler")
public class WarningInfoHandler {

    @Autowired
    private WarningInfoDao warningInfoDao;

    public WarningInfoDao getWarningInfoDao() {
        return warningInfoDao;
    }

    public void setWarningInfoDao(WarningInfoDao warningInfoDao) {
        this.warningInfoDao = warningInfoDao;
    }

    @Autowired
    private DriverInfoDao driverInfoDao;

    public DriverInfoDao getDriverInfoDao() {
        return driverInfoDao;
    }

    public void setDriverInfoDao(DriverInfoDao driverInfoDao) {
        this.driverInfoDao = driverInfoDao;
    }

    /**
     * 接收保存预警消息
     * @param recieveStr 预警消息
     */
    @SuppressWarnings({ "unchecked" })
    public String setWarning(String recieveStr) {
        //回复信息
        JSONObject resp = new JSONObject();
        resp.put("fromtype", "cloud");
        resp.put("datatype", "saveWarningMsg");
        JSONObject jsonObject = JSONObject.parseObject(recieveStr);
        //判断是否已经注册过
       WarningInfo warningInfo = JSONObject.parseObject(recieveStr, WarningInfo.class);
       String account=new String(jsonObject.getString("account"));
        String hql="from DriverInfo where account=?";
        List<DriverInfo> driverInfos = driverInfoDao.find(hql,account);
        DriverInfo driverInfo = driverInfos.get(0);
        if (driverInfo != null) {
            System.out.println("driverID=" + driverInfo.getId());
            warningInfo.setDriverId(driverInfo.getId());
            System.out.println(warningInfo.getDriverId()+warningInfo.getFatigue()+warningInfo.getGrade()+warningInfo.getTime());
        //存储预警信息
            warningInfoDao.save(warningInfo);
            resp.put("isSuccess", true);
            resp.put("content", "成功存储预警信息");
        } else {
            resp.put("isSuccess", false);
            resp.put("content", "预警信息存储失败");
        }
        return resp.toString();
    }

    /**
     * 查询预警消息
     * @param recieveStr 请求预警消息
     */
    public String getWarning(String recieveStr) {
        JSONObject resp = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(recieveStr);
        WarningInfo warningInfo = new WarningInfo();
        //Vehicle veh = JSONObject.parseObject(recJson, Vehicle.class);    //车载发来的消息存到veh中
        String account = new String(jsonObject.getString("account"));
        String hql = "from DriverInfo where account=?";
        List<DriverInfo> driverInfos = driverInfoDao.find(hql, account);
        DriverInfo driverInfo = driverInfos.get(0);
        if (driverInfo != null) {
            System.out.println("driverID=" + driverInfo.getId());
            //存储预警信息
            String hql2 = "from WarningInfo where driver_id=?";
            List<WarningInfo> warningInfos = warningInfoDao.find(hql2, driverInfo.getId());
            System.out.println(warningInfos);
            resp.put("isSuccess", true);
            resp.put("content", "成功获取预警信息");
        } else {
            resp.put("isSuccess", false);
            resp.put("content", "预警信息获取失败");
        }
        return resp.toString();
    }
}
