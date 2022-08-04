package com.example.mcc_deliveryapp.Rider;

public class model {

	String receivercontact,receiverlocation,receivername,sendercontact,senderlocation,
			vehicletype, sendername, customernotes, fee, OrderID, parcelstatus, ridernum, ridername,
			userParcelStatus, defaultUserNum, DatePlace;

	public model() {
	}

	public model(String receivercontact, String receiverlocation, String receivername,
				 String sendercontact, String senderlocation, String sendername,
				 String vehicletype, String customernotes, String fee, String OrderID,
				 String parcelstatus, String ridernum, String ridername, String userParcelStatus,
				 String defaultUserNum, String DatePlace) {
		this.receivercontact = receivercontact;
		this.receiverlocation = receiverlocation;
		this.receivername = receivername;
		this.sendercontact = sendercontact;
		this.senderlocation = senderlocation;
		this.sendername = sendername;
		this.vehicletype = vehicletype;
		this.customernotes = customernotes;
		this.fee = fee;
		this.OrderID = OrderID;
		this.parcelstatus = parcelstatus;
		this.ridernum = ridernum;
		this.ridername = ridername;
		this.userParcelStatus = userParcelStatus;
		this.defaultUserNum = defaultUserNum;
		this.DatePlace = DatePlace;
	}

	public String getDatePlace() {return DatePlace;}

	public String getDefaultUserNum() {return defaultUserNum;}

	public String getUserParcelStatus() {return userParcelStatus;}

	public String getRidername() {return ridername;}

	public String getRidernum() {return ridernum;}

	public String getParcelstatus() {return parcelstatus;}

	public String getOrderID() {return OrderID;}

	public String getFee() {return fee;}

	public String getVehicletype() {return vehicletype;}

	public String getCustomerNotes() {
		return customernotes;
	}

	public String getReceivercontact() {
		return receivercontact;
	}

	public String getReceiverlocation() {return receiverlocation;}

	public String getReceivername() {
		return receivername;
	}

	public String getSendercontact() {
		return sendercontact;
	}

	public String getSenderlocation() {
		return senderlocation;
	}

	public String getSendername() {
		return sendername;
	}

	public void setDatePlace(String datePlace) {DatePlace = datePlace;}

	public void setDefaultUserNum(String defaultUserNum) {this.defaultUserNum = defaultUserNum;}

	public void setUserParcelStatus(String userParcelStatus) {this.userParcelStatus = userParcelStatus;}

	public void setRidername(String ridername) {this.ridername = ridername;}

	public void setRidernum(String ridernum) {this.ridernum = ridernum;}

	public void setParcelstatus(String parcelstatus) {this.parcelstatus = parcelstatus;}

	public void setOrderID(String orderID) {OrderID = orderID;}

	public void setFee(String fee) {this.fee = fee;}

	public void setVehicletype(String vehicletype) {this.vehicletype = vehicletype;}

	public void setCustomerNotes(String customernotes) {
		this.customernotes = customernotes;
	}

	public void setReceivercontact(String receivercontact) {
		this.receivercontact = receivercontact;
	}

	public void setReceiverlocation(String receiverlocation) {this.receiverlocation = receiverlocation;}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public void setSendercontact(String sendercontact) {
		this.sendercontact = sendercontact;
	}

	public void setSenderlocation(String senderlocation) {
		this.senderlocation = senderlocation;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
}
