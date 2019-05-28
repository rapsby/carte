package com.o2o.action.server.db;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Table(name="tbl")
public class UserEntity {
	public enum UserRole{
		USER,
		ADMIN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String userName;
	private Integer age;
	private Date createdAt;
	
	@Column(name="role")
	@Enumerated(EnumType.ORDINAL)
	private UserRole role;
	@PrePersist
	public void beforeCreate() {
		createdAt = new Date();
	}
	
	public UserEntity() {
	}
	
	public UserEntity(String userName) {
		this.userName = userName;
	}
	
	public UserEntity(String userName, Integer age, UserRole role) {
		this.userName = userName;
		this.age = age;
		this.role = role;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setUserName(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	

	
/*
	@ManyToOne(optional = false)
	@JoinColumn
	@JsonIgnore
	MealMenu mealMenu;

	@Column(nullable = false)
	MealType mealType;

	String menu1;
	String menu2;
	String menu3;
	String menu4;
	String menu5;
	String menu6;
	String menu7;
*/
	


}
