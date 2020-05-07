package com.mboaeat.common.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Address data")
@Schema(name = "AddressInfo",
        description = "Supertype of all address methods",
        discriminatorProperty = "type",
        subTypes = {
            ShippingAddressInfo.class,
            BillingAddressInfo.class
        }
)
@JsonTypeInfo(
        use = NAME,
        include = PROPERTY,
        property = "type",
        defaultImpl = AddressTypeInfo.class,
        visible = true
)
@JsonSubTypes({
        @Type(value = ShippingAddressInfo.class, name = "SHIPPING"),
        @Type(value = BillingAddressInfo.class, name = "BILLING")
})
public abstract class AddressDTO extends AbstractBaseDTO{

    @NotBlank
    @Schema(description = "The client address first name", required = true)
    private String name;

    @NotBlank
    @Schema(description = "The client address last name", required = true)
    private String lastName;

    @Schema(description = "The client address mobile phone number")
    private String mobileNumber;

    @NotBlank
    @Schema(description = "The client address country", required = true, accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "Cameroon")
    private String country;

    @NotNull
    @Schema(description = "The street address")
    private StreetInfo streetInfo;

    @Schema(description = "The client address zip code")
    private String postBox;

    @NotBlank
    @Schema(description = "The client address city", required = true)
    private String city;

    @Schema(description = "The client address type", required = true)
    private AddressTypeInfo type;

    //public abstract void email(String email);
}
