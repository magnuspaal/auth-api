package com.magnus.authapi.controllers.error;

import com.magnus.authapi.controllers.dto.BaseResponse;
import lombok.*;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse extends BaseResponse {
  private ArrayList<String> codes;
}