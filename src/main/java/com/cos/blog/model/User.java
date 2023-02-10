package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴!
//ORM -> Java(다른언어)Object 를 -> 테이블로 매핑해주는 기술.
@Entity // User클래스가 MySQL 에 테이블이 자동생성된다.
// @DynamicInsert // insert 할 때 null 인 필드를 제외시켜준다. 
public class User {
  
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // MySQL에서 auto_increment 
	
	@Column(nullable = false, length = 30)
	private String username; // 아이디
  
	@Column(nullable = false, length = 100) // 해쉬로 변경해서 비밀번호를 암호화 할것이기때문에 넉넉하게 100으로 제한둔다!
	private String password;
	
	@Column(nullable = false, length = 50)
	private String Email; // Email, email
	
	//@ColumnDefault("user")
	//DB는 RoleType 이라는게 없다. 
    @Enumerated(EnumType.STRING)
	private RoleType role; // 원래는 Enum 을쓴다. -데이터의 도메인을 만들 수 있다! // ADMIN, USERS
		
	@CreationTimestamp // 시간이 자동으로 입력이 된다! 
	private Timestamp createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


}


