package com.sm.userservice.model;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Container(containerName = "smcosmocollection")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteUser {
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private String id;
	@JsonProperty("fname")
	private String fname;
	@PartitionKey
	@JsonProperty("lname")
	private String lname;
	@JsonProperty("age")
	private String age;
	
	public SiteUser() {
	}
	
	public SiteUser(String fname, String lname, String age){
		this.fname = fname;
		this.lname = lname;
		this.age = age;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setFname(String name) {
		this.fname = name;
	}
	public String getFname() {
		return fname;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAge() {
		return age;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getLname() {
		return lname;
	}

	@Override
	public String toString() {
		String str = new StringBuilder("[").append(fname).append(", ").append(lname).append(", ")
				.append(age).append(",").append("]").toString();
		return str;
	}
}
