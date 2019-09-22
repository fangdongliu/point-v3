package cn.fdongl.point.common.util;

import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseController <T>{
    @Autowired
    protected T service;
}
