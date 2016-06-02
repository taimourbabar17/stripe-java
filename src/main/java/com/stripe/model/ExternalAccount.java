package com.stripe.model;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter @Setter @EqualsAndHashCode(callSuper=false)
public class ExternalAccount extends APIResource implements HasId, MetadataStore<ExternalAccount> {
	String id;
	String object;
	String account;
	String customer;
	Map<String, String> metadata;

	public String getInstanceURL() {
		if (this.getCustomer() != null) {
			return String.format("%s/%s/sources/%s", classURL(Customer.class), this.getCustomer(), this.getId());
		} else if (this.getAccount() != null) {
			return String.format("%s/%s/external_accounts/%s", classURL(Account.class), this.getAccount(), this.getId());
		} else {
			return null;
		}
	}

	public ExternalAccount verify(Map<String, Object> params) throws
			AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return verify(params, null);
	}

	public ExternalAccount verify(Map<String, Object> params, RequestOptions options) throws
			AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		if (this.getCustomer() != null) {
			return request(RequestMethod.POST, String.format("%s/verify", this.getInstanceURL()), params, ExternalAccount.class, options);
		} else {
			throw new InvalidRequestException("Only customer bank accounts can be verified in this manner.",
				null, null, null, 0, null);
		}
	}

	public ExternalAccount update(Map<String, Object> params) throws
			AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return update(params, null);
	}

	public ExternalAccount update(Map<String, Object> params, RequestOptions options)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return request(RequestMethod.POST, this.getInstanceURL(), params, ExternalAccount.class, options);
	}

	public DeletedExternalAccount delete() throws AuthenticationException,
			InvalidRequestException, APIConnectionException,
			CardException, APIException {
		return delete(null);
	}

	public DeletedExternalAccount delete(RequestOptions options) throws
			AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		return request(RequestMethod.DELETE, this.getInstanceURL(), null, DeletedExternalAccount.class, options);
	}
}
