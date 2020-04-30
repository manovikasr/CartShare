package com.cmpe275project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pools")
public class Pool {
	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Pool ID is Mandatory")
	@NotEmpty(message = "Pool ID is Mandatory")
	@NotBlank(message = "Pool ID is Mandatory")
	@Column(name = "pool_id")
	private String pool_id;
	
	@NotNull(message = "Pool Name is Mandatory")
	@NotEmpty(message = "Pool Name is Mandatory")
	@NotBlank(message = "Pool Name is Mandatory")
	@Column(name = "pool_name")
	private String pool_name;
	
	@NotNull(message = "Neighbourhood Name is Mandatory")
	@NotEmpty(message = "Neighbourhood Name is Mandatory")
	@NotBlank(message = "Neighbourhood Name is Mandatory")
	@Column(name = "neighbourhood_name")
	private String neighbourhood_name;
	
	@NotNull(message = "Pool Description is Mandatory")
	@NotEmpty(message = "Pool Description is Mandatory")
	@NotBlank(message = "Pool Description is Mandatory")
	@Column(name = "pool_desc")
	private String pool_desc;
	
	@NotNull(message = "Pool Zip is Mandatory")
	@Column(name = "pool_zip")
	private int pool_zip;
	
	@NotNull(message = "Pool Leader is Mandatory")
	@Column(name = "pool_leader_id")
	private long pool_leader_id;
	
	@OneToMany(mappedBy = "pool")
    private List<User> user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}
	
	public String getPool_name() {
		return pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getNeighbourhood_name() {
		return neighbourhood_name;
	}

	public void setNeighbourhood_name(String neighbourhood_name) {
		this.neighbourhood_name = neighbourhood_name;
	}

	public String getPool_desc() {
		return pool_desc;
	}

	public void setPool_desc(String pool_desc) {
		this.pool_desc = pool_desc;
	}

	public int getPool_zip() {
		return pool_zip;
	}

	public void setPool_zip(int pool_zip) {
		this.pool_zip = pool_zip;
	}

	public long getPool_leader_id() {
		return pool_leader_id;
	}

	public void setPool_leader_id(long pool_leader_id) {
		this.pool_leader_id = pool_leader_id;
	}	
	

}
