package com.xm.autenticacion.modelo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTokenAdministrador {
    @JsonProperty("token_type")
    private String tipoToken;
    @JsonProperty("expires_in")
    private int segundosValidos;
    @JsonProperty("ext_expires_in")
    private int segundoValidosExtendido;
    @JsonProperty("access_token")
    private String tokenAcceso;
}
