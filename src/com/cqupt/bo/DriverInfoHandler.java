package com.cqupt.bo;

import com.alibaba.fastjson.JSONObject;
import com.cqupt.dao.DriverInfoDao;
import com.cqupt.entity.DriverInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.List;

@Service("driverInfoHandler")
public class DriverInfoHandler {

    @Autowired
    private DriverInfoDao driverInfoDao;

    public DriverInfoDao getDriverInfoDao() {
        return driverInfoDao;
    }

    public void setDriverInfoDao(DriverInfoDao driverInfoDao) {
        this.driverInfoDao = driverInfoDao;
    }

    /**
     * 接收用户注册信息（有返回值）
     *
     * @param recieveStr 接收信息
     */
    @SuppressWarnings({"unchecked"})
    public String register(String recieveStr) {
        //回复信息
        JSONObject resp = new JSONObject();
        resp.put("fromtype", "cloud");
        resp.put("datatype", "registerResponse");
        //判断是否已经注册过
        DriverInfo driverInfo = JSONObject.parseObject(recieveStr, DriverInfo.class);
        System.out.println("driverAccount=" + driverInfo.getAccount() + ",driverpassword=" + driverInfo.getPassword() + ",drivername=" + driverInfo.getName() + ",driverphone=" + driverInfo.getPhone());
        String account = driverInfo.getAccount();
        String hql = "from DriverInfo where account=?";
        if (driverInfoDao.find(hql, account).isEmpty()) {                 //在数据库中查找account对应驾驶员信息
            driverInfoDao.save(driverInfo);
            resp.put("isSuccess", true);
            resp.put("content", "用户注册成功");
        } else {
            resp.put("isSuccess", false);
            resp.put("content", "该id已被注册，请更换id再注册");
        }
        return resp.toString();
    }

    /**
     * 接收用户登录请求（有返回值）
     *
     * @param recieveStr 接收信息
     */
    @SuppressWarnings({"unchecked"})
    public String login(String recieveStr) {
        JSONObject resp = new JSONObject();
        resp.put("fromtype", "cloud");
        resp.put("datatype", "loginResponse");

        JSONObject jsonObject = JSONObject.parseObject(recieveStr);
        //车载发来的消息存到driverinfo中
        String account = jsonObject.getString("account");
        String pwd = new String(jsonObject.getString("password"));
        String hql = "from DriverInfo where account=?";
        List<DriverInfo> driverInfos = driverInfoDao.find(hql, account);
        if (driverInfos == null) {
            resp.put("isSuccesslogin", false);
            resp.put("content", "User id is wrong");
            return resp.toString();
        }
        if (driverInfos.isEmpty()) {
            resp.put("isSuccesslogin", false);
            resp.put("content", "account not register");
            return resp.toString();
        }
        for (DriverInfo driverInfo : driverInfos) {
            if (driverInfo != null) {
                String password = driverInfo.getPassword();
                if (pwd.equals(password)) {
                    resp.put("isSuccesslogin", true);
                    resp.put("content", "Login success");
                } else {
                    resp.put("isSuccesslogin", false);
                    resp.put("content", "password is wrong");
                }
            }
        }
        return resp.toString();
    }
}
