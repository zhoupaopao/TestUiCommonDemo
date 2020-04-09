package com.example.lib_resource.bean;


import java.io.Serializable;

/**
 * 医嘱
 *
 */
public class MedicalOrderDTO extends BaseBean implements Serializable {
	//setter和getter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return this.number;
	}

	// 属性
	// 主属性
	private Long id;
	// 医嘱单编号
	private String number;

}