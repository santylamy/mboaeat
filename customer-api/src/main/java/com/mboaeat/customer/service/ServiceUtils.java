package com.mboaeat.customer.service;

import com.mboaeat.common.domain.EmailAddress;
import com.mboaeat.common.domain.FixedPhoneNumber;
import com.mboaeat.common.domain.MobilePhoneNumber;
import com.mboaeat.common.dto.AddressDTO;
import com.mboaeat.common.dto.AddressType;
import com.mboaeat.common.dto.Client;
import com.mboaeat.customer.domain.*;

public class ServiceUtils {

    private ServiceUtils() {
    }

    public static Customer toCustomer(Client client){
        return Customer
                .builder()
                .clientName(
                   ClientName
                           .builder()
                           .name(client.getName())
                           .build()
                )
                .address(
                        toAddress(client.getAddress()))
                .fixedPhoneNumber(FixedPhoneNumber.builder().value(client.getAddress().getFixedNumber()).build())
                .build();
    }

    private static Address toAddress(AddressDTO addressDTO) {
        AddressType addressType = addressDTO.getType();

        Address.AddressBuilder<?, ?> address = null;

        switch (addressType){
            case BILLING:
                address = BillingAddress
                        .builder()
                        .emailAddress(
                                EmailAddress.builder().value(addressDTO.getEmail()).build())
                        .addressStreet(
                                AddressStreet
                                        .builder()
                                        .city(addressDTO.getCity())
                                        .postBox(addressDTO.getPostBox())
                                        .name_one(addressDTO.getStreetNameOne())
                                        .name_two(addressDTO.getStreetNameTwo())
                                        .build())

                        .personAddressName(
                                PersonAddressName
                                        .builder()
                                        .name(addressDTO.getName())
                                        .firstName(addressDTO.getLastName())
                                        .build())
                        .mobilePhoneNumber(MobilePhoneNumber.builder().value(addressDTO.getMobileNumber()).build());
                break;
            case DELIVERY:
                address = DeliveryAddress
                        .builder()
                        .addressStreet( AddressStreet
                                .builder()
                                .city(addressDTO.getCity())
                                .postBox(addressDTO.getPostBox())
                                .name_one(addressDTO.getStreetNameOne())
                                .name_two(addressDTO.getStreetNameTwo())
                                .build())
                        .personAddressName(
                                PersonAddressName
                                .builder()
                                .name(addressDTO.getName())
                                .firstName(addressDTO.getLastName())
                                .build())
                        .mobilePhoneNumber(MobilePhoneNumber.builder().value(addressDTO.getMobileNumber()).build());
                break;
            default:
                address = Address.builder();
        }

        return address.build();
    }
}
