package ir.iconish.model.creditscoreclient.dto;

import java.io.Serializable;

public class AvailableDTO implements Serializable {

	Boolean validMobile;
	Boolean isAvailable;


	public Boolean getValidMobile() {
		return validMobile;
	}

	public void setValidMobile(Boolean validMobile) {
		this.validMobile = validMobile;
	}

	public Boolean getAvailable() {
		return isAvailable;
	}

	public void setAvailable(Boolean available) {
		isAvailable = available;
	}

	@Override
	public String toString() {
		return "AvailableDTO{" +
				"validMobile=" + validMobile +
				", isAvailable=" + isAvailable +
				'}';
	}
}
