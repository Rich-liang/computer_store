package com.ywl.store.controller;

import com.ywl.store.controller.ex.*;
import com.ywl.store.entity.User;
import com.ywl.store.service.IUserService;
import com.ywl.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> req(User user) {
        JsonResult<Void> result = new JsonResult<>();
        userService.reg(user);
        result.setState(200);
        result.setMessage("用户注册成功");
        return result;
    }


    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("changePassword")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("getByUid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("changeInfo")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        userService.changeInfo(getuidFromSession(session), getUsernameFromSession(session), user);
        return new JsonResult<>(OK);
    }

    /** 设置上传文件最大值 */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 限制上传文件类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @RequestMapping("changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file){
        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        //判断文件大小是否超过限制
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        //判断文件类型是否是规定类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }

        //在tomcat下的绝对路径
        String parent = session.getServletContext().getRealPath("upload");
        //dir指向这个路径
        File dir = new File(parent);
        if (!dir.exists()){ // 目录不存在则创建
            dir.mkdirs();
        }
        //获取文件名称
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //生成新的文件名字
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //创建新文件
        File dest = new File(dir, filename); //空文件
        //file数据写入这个空文件中
        try {
            file.transferTo(dest);
        }
        catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }


        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //头像路径
        String avatar = "/upload/" + filename;
        //写入数据库
        userService.changeAvatar(uid, avatar, username);
        //返回头像路径
        return new JsonResult<>(OK, avatar);
    }
}
