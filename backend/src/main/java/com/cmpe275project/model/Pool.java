package com.cmpe275project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pools")
public class Pool {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Pool Name is Mandatory")
	@NotEmpty(message = "Pool Name is Mandatory")
	@NotBlank(message = "Pool Name is Mandatory")
	@Column(name = "pool_name")
	private String poolname;
	
	@NotNull(message = "Neighbourhood Name is Mandatory")
	@NotEmpty(message = "Neighbourhood Name is Mandatory")
	@NotBlank(message = "Neighbourhood Name is Mandatory")
	@Column(name = "neighbourhood_name")
	private String neighbourhoodname;
	
	@NotNull(message = "Pool Description is Mandatory")
	@NotEmpty(message = "Pool Description is Mandatory")
	@NotBlank(message = "Pool Description is Mandatory")
	@Column(name = "pool_desc")
	private String pooldesc;
	
	@NotNull(message = "Pool Zip is Mandatory")
	@Column(name = "pool_zip")
	private int poolzip;
	
	@NotNull(message = "Pool Leader is Mandatory")
	@Column(name = "pool_leader_id")
	private long poolleaderid;
	
	@NotNull(message = "Pool Rating is Mandatory")
	@NotEmpty(message = "Pool Rating is Mandatory")
	@NotBlank(message = "Pool Rating is Mandatory")
	@Column(name = "pool_rating")
	private String poolrating;
	
	@OneToOne(mappedBy = "pool")
    private User user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPoolname() {
		return poolname;
	}

	public void setPoolname(String poolname) {
		this.poolname = poolname;
	}

	public String getNeighbourhoodname() {
		return neighbourhoodname;
	}

	public void setNeighbourhoodname(String neighbourhoodname) {
		this.neighbourhoodname = neighbourhoodname;
	}

	public String getPooldesc() {
		return pooldesc;
	}

	public void setPooldesc(String pooldesc) {
		this.pooldesc = pooldesc;
	}

	public int getPoolzip() {
		return poolzip;
	}

	public void setPoolzip(int poolzip) {
		this.poolzip = poolzip;
	}

	public long getPoolleaderid() {
		return poolleaderid;
	}

	public void setPoolleaderid(long poolleaderid) {
		this.poolleaderid = poolleaderid;
	}

	public String getPoolrating() {
		return poolrating;
	}

	public void setPoolrating(String poolrating) {
		this.poolrating = poolrating;
	}

	
	
	
	

}
