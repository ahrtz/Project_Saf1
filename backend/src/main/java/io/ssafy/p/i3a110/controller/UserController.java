package io.ssafy.p.i3a110.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.ssafy.p.i3a110.apihelper.GitHubRestApiHelper;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.FollowService;
import io.ssafy.p.i3a110.service.LikeService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {
//    private static final String BE_BASE_URL = "http://localhost:3000";
    private static final String BE_BASE_URL = "http://i3a110.p.ssafy.io:3000";
    private static final String regExp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,16}$";
    
    private GitHubRestApiHelper helper;

    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private LikeService likeService;

    @Value("${application.staticPath}")
    private String staticPath;

    @PostMapping("/users")
    @ApiOperation(value = "키워드로 회원 조회")
    public List<HashMap<String, String>> findUsers(@RequestBody HashMap<String, String> map) {
        String keyword = map.get("keyword");
        List<UserDto> userList = userService.findUsers(keyword);
        List<HashMap<String, String>> output = new ArrayList<HashMap<String,String>>();
        for(UserDto user : userList) {
        	HashMap<String, String> userinfo = new HashMap<String, String>();
    		userinfo.put("id", String.valueOf(user.getId()));
    		userinfo.put("email", user.getEmail());
    		userinfo.put("nickname", user.getNickname());
    		userinfo.put("img", user.getImg());
    		userinfo.put("intro", user.getIntro());
    		output.add(userinfo);
        }
        return output;
    }

    @GetMapping("/users/{email}")
    @ApiOperation(value = "회원 단일 조회")
    public Object findUserByEmail(@PathVariable String email) {
    	UserDto user = userService.findUserByEmail(email);
    	if(user==null) {
    		HashMap<String, String> msg = new HashMap<String, String>();
    		msg.put("errMsg", "해당 회원이 존재하지 않습니다.");
    		return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    	}
    	HashMap<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("id", String.valueOf(user.getId()));
		userinfo.put("email", user.getEmail());
		userinfo.put("nickname", user.getNickname());
		userinfo.put("img", user.getImg());
		userinfo.put("intro", user.getIntro());
        return new ResponseEntity<>(userinfo, HttpStatus.OK);
    }
    
    @GetMapping("/users")
    @ApiOperation(value = "회원 단일 조회 (회원 id (PK))")
    public HashMap<String, String> findUserById(int id) {
    	UserDto user = userService.findUserById(id);
    	HashMap<String, String> userinfo = new HashMap<String, String>();
    	userinfo.put("id", String.valueOf(user.getId()));
    	userinfo.put("email", user.getEmail());
    	userinfo.put("nickname", user.getNickname());
    	userinfo.put("img", user.getImg());
    	userinfo.put("intro", user.getIntro());
    	userinfo.put("gitUrl", user.getGitUrl());
    	
    	List<Integer> follower = followService.getFollowInfo(0, user.getId());
    	List<Integer> following = followService.getFollowInfo(1, user.getId());
    	userinfo.put("followerCnt", String.valueOf(follower.size()));
    	userinfo.put("followingCnt", String.valueOf(following.size()));
    	userinfo.put("likeCnt", String.valueOf(likeService.getLikeCntByUid(user.getId())));
    	
    	return userinfo;
    }

    @Auth
    @PutMapping("/users")
    @ApiOperation(value = "회원 정보 수정")
    public void updateUser(HttpSession httpSession,
                           @RequestParam(required = false) MultipartFile file,
                           @RequestParam String pwd,
                           @RequestParam String nickname,
                           @RequestParam String gitId,
                           @RequestParam String gitUrl,
                           @RequestParam String intro,
                           @RequestParam String gitToken,
                           @RequestParam String isCertified) throws Exception {

        String userEmail = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(userEmail);
        if(!pwd.equals("") && pwd != null && !pwd.equals("undefined")) user.setPwd(pwd);
        user.setNickname(nickname);
        user.setGitId(gitId);
        user.setGitUrl(gitUrl);
        user.setIntro(intro);
        user.setGitToken(gitToken);
        helper = new GitHubRestApiHelper();
        if(!helper.checkOauth(gitId, gitToken)) {
        	user.setGitId("");
        	user.setGitToken("");
        }
        user.setIsCertified(Integer.parseInt(isCertified));

        if (file != null) {
            long timestamp = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder(staticPath);
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = timestamp + "." + extension;

            file.transferTo(new File(builder.append("/")
                    .append(fileName)
                    .toString()));
            user.setImg(new StringBuilder(BE_BASE_URL)
                    .append("/users/image/")
                    .append(fileName)
                    .toString());
        }
        userService.updateUser(user);
    }

    @Auth
    @DeleteMapping("/users")
    @ApiOperation(value = "회원 탈퇴")
    public void deleteById(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);

        userService.deleteById(user.getId());
    }

    @PostMapping("/users/login")
    @ApiOperation(value = "로그인")
    public Object login(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = map.get("email");
        String pwd = map.get("pwd");
        UserDto user = userService.findUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (pwd.equals(user.getPwd())) {
            httpSession.setAttribute("isLoggedIn", true);
            httpSession.setAttribute("email", email);
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/logout")
    @ApiOperation(value = "로그아웃")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @GetMapping("/users/is-logged-in")
    @ApiOperation(value = "로그인 체크")
    public boolean isLoggedIn(HttpSession httpSession) {
        boolean result;
        if (httpSession.getAttribute("isLoggedIn") == null) {
            result = false;
        } else {
            result = (boolean) httpSession.getAttribute("isLoggedIn");
        }
        return result;
    }

    @Auth
    @GetMapping("/users/me")
    @ApiOperation(value = "내 정보 조회")
    public Object me(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<Object, Object> form = objectMapper.convertValue(user, HashMap.class);
        form.remove("pwd");
        return form;
    }

    @PostMapping("/users/signup")
    @ApiOperation(value = "회원 가입")
    public Object signup(@RequestParam(required = false) MultipartFile file,
                       @RequestParam String email,
                       @RequestParam String pwd,
                       @RequestParam(required = false) String nickname,
                       @RequestParam String gitId,
                       @RequestParam String gitUrl,
                       @RequestParam String intro,
                       @RequestParam String gitToken,
                       @RequestParam String isSocial,
                       @RequestParam String isCertified) throws Exception {

        UserDto user = userService.findUserByEmail(email);
        if(user != null) {
    		HashMap<String, String> msg = new HashMap<String, String>();
    		msg.put("errMsg", "해당 Email이 존재합니다.");
        	return new ResponseEntity<>(msg ,HttpStatus.BAD_REQUEST);
        }
        if(!pwd.matches(regExp)) {
        	HashMap<String, String> msg = new HashMap<String, String>();
        	msg.put("errMsg", "비밀번호 형식이 잘못되었습니다.");
        	return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        user = new UserDto();
        user.setEmail(email);
        user.setPwd(pwd);
        if(nickname == null) nickname = "anonymous";
        System.out.println(nickname);
        user.setNickname(nickname);
        user.setGitId(gitId);
        user.setGitUrl(gitUrl);
        user.setIntro(intro);
        user.setGitToken(gitToken);
        helper = new GitHubRestApiHelper();
        if(!helper.checkOauth(gitId, gitToken)) {
        	user.setGitId("");
        	user.setGitToken("");
        }
        user.setIsSocial(Integer.parseInt(isSocial));
        user.setIsCertified(Integer.parseInt(isCertified));

        if (file == null) {
            user.setImg(null);
        } else {
            long timestamp = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder(staticPath);
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = timestamp + "." + extension;

            file.transferTo(new File(builder.append("/")
                    .append(fileName)
                    .toString()));
            user.setImg(new StringBuilder(BE_BASE_URL)
                    .append("/users/image/")
                    .append(fileName)
                    .toString());
        }

        userService.insertUser(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @GetMapping("/users/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(staticPath + "/" + fileName);
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    
    @Auth
    @GetMapping("/users/cancel")
    @ApiOperation(value = "GitToken 검증 취소")
    public void cancelToken(HttpSession session) {
    	String email = (String)session.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	
    	userService.cancelToken(user.getId());
    }
    
}
