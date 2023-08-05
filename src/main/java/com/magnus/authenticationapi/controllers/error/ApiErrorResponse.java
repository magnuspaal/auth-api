package com.magnus.authenticationapi.controllers.error;

import lombok.*;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse extends BaseResponse {
  private ArrayList<String> codes;
}