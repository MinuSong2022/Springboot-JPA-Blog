package com.cos.blog.test;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

//html 파일이 아니라 data 를 리턴해주는 controller = RestController 
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입
	private UserRepository  userRepository;
	
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있을때 update 를 해주고
	// save함수는 id를 전달하면 해당 id 에 대한 데이터가 없을때 insert를 한다.
	// email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
	    }
		return "삭제되었습니다. id : "+id;
	}
	
	@Transactional //함수 종료시에 자동 commit 이 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 요청 => Java Object(MessageConverter의  Jackson라이브러리가 변환해서 받아준다.)
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user =userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword()); 
		user.setEmail(requestUser.getEmail());
		
	//	userRepository.save(requestUser);
		
	// 더티체킹	
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/user")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 2건의 데이터를 리턴 받아볼 예정.
	@GetMapping("/dummy/user2")
	public Page<User> pageList(@PageableDefault (size= 2, sort="id", direction =Direction.DESC) Pageable pageable){
	Page<User> users =	userRepository.findAll(pageable);
	return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있다.
	// http://localhost/8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//Optional 로 User 객체를 감싸서 가져올테니 null 인지 아닌지 판단해서 return 한다. 
		
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 사용자가 없습니다.");
			}
		});
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터)  -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter 라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게되면 MessagaConverter 가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json 으로 변환해서 브라우저에게 던져줍니다. 
		return user;
	}

	private void orElseThrow(Supplier<IllegalArgumentException> supplier) {
		// TODO Auto-generated method stub
		
	}

	//http://localhost:8000/blog/dummy/join (요청)
	//http 의 body에 username,passwordd, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value 형태로 잡아준다.(약속된 규칙)  	
		System.out.println("id: "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
	 	System.out.println("role : "+user.getRole());
	 	System.out.println("createDate : "+user.getCreateDate());
		 
	 	user.setRole(RoleType.USER);
	 	userRepository.save(user);
	 	return "회원가입이 완료되었습니다";
	}
}