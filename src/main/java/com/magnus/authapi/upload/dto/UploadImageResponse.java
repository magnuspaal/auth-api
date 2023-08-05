package com.magnus.authapi.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UploadImageResponse {
  private String filename;

  @Override
  public String toString() {
    return this.getFilename();
  }
}
