
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

	@Column(name = "donation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate		dateDonation;

	@Column(name = "amount")
	@NotNull
	@Min(0)
	private Double		amount; 

	@Column(name = "client")
	@NotEmpty
	private String		client;
	
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause		cause;
	
	// Getters and Setters

	public LocalDate getDateDonation() {
		return dateDonation;
	}

	public void setDateDonation(LocalDate dateDonation) {
		this.dateDonation = dateDonation;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Cause getCause() {
		return this.cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}
	
}
