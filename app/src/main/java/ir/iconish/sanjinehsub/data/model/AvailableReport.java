package ir.iconish.sanjinehsub.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class AvailableReport implements Serializable {

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

    @NonNull
    @Override
	public String toString() {
		return "AvailableDTO{" +
				"validMobile=" + validMobile +
				", isAvailable=" + isAvailable +
				'}';
	}
}
