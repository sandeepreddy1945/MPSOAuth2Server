package com.app.mps.oauth.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Audited
@JsonPropertyOrder({ "id", "username", "password", "accountExpired", "accountLocked", "credentialsExpired", "enabled",
		"authorities" })
@NamedQueries({ @NamedQuery(name = "@listAllUsers", query = "from User u"),
		@NamedQuery(name = "@deleteUserById", query = "delete from User u where u.id = :userId") })
public class User implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6732685477673143275L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonProperty("id")
	private Long id;

	@Column(name = "USER_NAME")
	@JsonProperty("username")
	private String username;

	@Column(name = "PASSWORD")
	@JsonProperty("password")
	private String password;

	@Column(name = "ACCOUNT_EXPIRED")
	@JsonProperty("accountExpired")
	private boolean accountExpired;

	@Column(name = "ACCOUNT_LOCKED")
	@JsonProperty("accountLocked")
	private boolean accountLocked;

	@Column(name = "CREDENTIALS_EXPIRED")
	@JsonProperty("credentialsExpired")
	private boolean credentialsExpired;

	@Column(name = "ENABLED")
	@JsonProperty("enabled")
	private boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
	@OrderBy
	@JsonProperty("authorities")
	// @JsonIgnore // need not ignore this.
	private List<Authority> authorities;

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired();
	}
}
