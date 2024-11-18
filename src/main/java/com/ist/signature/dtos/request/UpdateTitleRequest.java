package com.ist.signature.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTitleRequest {
    @NotBlank(message = "Title is required")
    @NotEmpty(message = "Title is required")
    @NotNull(message = "Title is required")
    private String title;
}
