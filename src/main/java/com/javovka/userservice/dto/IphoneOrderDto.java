package com.javovka.userservice.dto;

import com.javovka.userservice.models.Iphone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IphoneOrderDto {

    @NotNull
    private Iphone iphone;

    @NotNull
    private Integer quantities;

}
