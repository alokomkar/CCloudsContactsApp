package com.cclouds.contactutils;

public class ContactsItem {

	String mContactName;
	String mContactNumber;
	String mEmail;
	String mImageURL;

	/**
	 * @param mContactName
	 * @param mContactNumber
	 * @param mEmail
	 * @param mImageURL
	 */
	public ContactsItem(String mContactName, String mContactNumber,
			String mEmail, String mImageURL) {
		super();
		this.mContactName = mContactName;
		this.mContactNumber = mContactNumber;
		this.mEmail = mEmail;
		this.mImageURL = mImageURL;
	}

	/**
	 * Default Constructor
	 */
	public ContactsItem() {
		super();
	}

	/**
	 * @return the mContactName
	 */
	public String getmContactName() {
		return mContactName;
	}

	/**
	 * @param mContactName the mContactName to set
	 */
	public void setmContactName(String mContactName) {
		this.mContactName = mContactName;
	}

	/**
	 * @return the mContactNumber
	 */
	public String getmContactNumber() {
		return mContactNumber;
	}

	/**
	 * @param mContactNumber the mContactNumber to set
	 */
	public void setmContactNumber(String mContactNumber) {
		this.mContactNumber = mContactNumber;
	}

	/**
	 * @return the mEmail
	 */
	public String getmEmail() {
		return mEmail;
	}

	/**
	 * @param mEmail the mEmail to set
	 */
	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	/**
	 * @return the mImageURL
	 */
	public String getmImageURL() {
		return mImageURL;
	}

	/**
	 * @param mImageURL the mImageURL to set
	 */
	public void setmImageURL(String mImageURL) {
		this.mImageURL = mImageURL;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		/**
		 * Will be used in searching
		 * */
		return  mContactName + " "
				+ mContactNumber + " " + mEmail;
	}



}
