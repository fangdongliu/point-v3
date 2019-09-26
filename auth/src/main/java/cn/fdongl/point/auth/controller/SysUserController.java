package cn.fdongl.point.auth.controller;

import cn.fdongl.point.auth.repository.UserRepository;
import cn.fdongl.point.common.entity.User;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "getInfo")
//    @RolesAllowed({"admin", "professor"})
    public Object getInfo(@RequestParam(value = "userId") Long userId){
        Optional<User> theUser = userRepository.findById(userId);
        if (theUser.isPresent()){
            return Result.of(ErrorCode.SUCCESS ,theUser).addDetail("获取用户信息成功");
        }else{
            return Result.of(ErrorCode.FAIL).addDetail("更新用户信息失败");
        }
    }

    @PostMapping(value = "updateInfo")
    public Object updateInfo(
            @RequestParam Long userId,
            @RequestParam String newPassword,
            String newRealName
    ){
        Optional<User> theUser = userRepository.findById(userId);

        if(theUser.isPresent()){
            User user = theUser.get();
            if(null != newRealName) {
                user.setRealName(newRealName);
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return Result.of(ErrorCode.SUCCESS, theUser)
                    .addDetail("更新用户信息成功");
        }
        return Result.of(ErrorCode.FAIL).addDetail("更新用户信息失败");
    }

    @PostMapping(value = "addNew")
    @RolesAllowed({"admin", "professor"})
    public Object addNew(
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam String department
    ){
        User user = new User()
                .setDepartment(department)
                .setUsername(username)
                .setRealName(role)
                .setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user);
        return Result.of(ErrorCode.SUCCESS, user).addDetail("新增用户成功");
    }

    @PostMapping(value = "page")
//    @RolesAllowed({"admin", "professor"})
    public Object getAll(
            @RequestParam int pageIndex,
            @RequestParam int pageSize,
            @RequestParam(required = false,defaultValue = "%") String searchKey
    ){
        PageRequest pageRequest = PageRequest.of(pageIndex-1,pageSize);
        return Result.of(ErrorCode.SUCCESS,
                PageResult.ofPage(
                        userRepository
                                .findByUsernameLikeOrRealNameLike(searchKey,searchKey,pageRequest))
        ).addDetail("获取用户分页成功");
    }

    @PostMapping(value = "deleteBatch")
    @RolesAllowed({"admin", "professor"})
    public Object deleteBatch(@RequestBody List<User> userList){
        userRepository.deleteAll(userList);
        return Result.of(ErrorCode.SUCCESS).addDetail("删除用户成功");
    }
}
