package com.magnus.authapi.controllers.exception;

import com.magnus.authapi.controllers.dto.BaseResponse;
import lombok.*;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse extends BaseResponse {
  private ArrayList<String> codes;
}