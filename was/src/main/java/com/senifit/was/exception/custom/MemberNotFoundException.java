package com.senifit.was.exception.custom;

import com.senifit.was.exception.api.ApiException;
import com.senifit.was.exception.api.ApiExceptionDetails;

public class MemberNotFoundException extends ApiException {
  public MemberNotFoundException() {
    super(ApiExceptionDetails.MEMBER_NOT_FOUND);
  }
}
