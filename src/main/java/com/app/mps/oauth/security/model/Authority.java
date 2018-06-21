package com.app.mps.oauth.security.model;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "AUTHORITY", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Audited
@JsonPropertyOrder({ "id", "name" })
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5216272878670103048L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonProperty("id")
	private Long id;

	@Column(name = "NAME")
	@JsonProperty("name")
	private String name;

	@Override
	@JsonIgnore
	public String getAuthority() {
		return getName();
	}
}
