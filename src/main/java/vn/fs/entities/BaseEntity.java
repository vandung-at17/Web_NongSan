package vn.fs.entities;
//Định nghĩa đây là entity cha

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "createddate")
	private Date createdDate;
	
	@Column(name = "modifieddate")
	private Date modifiedDate;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedby")
	private String modifiedBy;
	
}
