package com.untech.mcrcb.web.model;

public class DownloadBackup {
	private Long id;
	
	private String name;
	
	private String time;
	
	private String address;
	
	private String fileName;
	
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "downloadBackup [id=" + id + ", name=" + name + ", time=" + time
				+ ", address=" + address + "]";
	}
	
	
}
