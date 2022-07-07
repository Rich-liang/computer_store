package com.ywl.store.controller;

import com.ywl.store.controller.ex.*;
import com.ywl.store.service.ex.*;
import com.ywl.store.service.ex.AccessDeniedException;
import com.ywl.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器类的基类
 */
public class BaseController {
    /**
     * 操作成功的状态码
     */
    public static final int OK = 200;

    /**
     * @ExceptionHandler用于统一处理方法抛出的异常
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<Void>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(5000);
            result.setMessage("用户名占用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户不存在");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("密码错误");
        } else if (e instanceof InsertException) {
            result.setState(5003);
            result.setMessage("插入数据未知异常");
        } else if (e instanceof UpdateException) {
            result.setState(5004);
            result.setMessage("更新数据未知异常");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(5005);
            result.setMessage("收货地址超过上限");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(5006);
            result.setMessage("收货地址数据不存在");
        } else if (e instanceof AccessDeniedException) {
            result.setState(5007);
            result.setMessage("非法访问的异常");
        } else if (e instanceof DeleteException) {
            result.setState(5008);
            result.setMessage("删除数据未知异常");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(5009);
            result.setMessage("尝试访问的商品数据不存在");
        }else if (e instanceof CartNotFoundException) {
            result.setState(5010);
            result.setMessage("尝试访问的购物车数据不存在");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileStateException) {
            result.setState(6002);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileTypeException) {
            result.setState(6003);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    protected final Integer getuidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}