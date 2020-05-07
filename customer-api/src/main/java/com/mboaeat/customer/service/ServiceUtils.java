package com.mboaeat.customer.service;

import com.mboaeat.common.domain.EmailAddress;
import com.mboaeat.common.domain.FixedPhoneNumber;
import com.mboaeat.common.domain.MobilePhoneNumber;
import com.mboaeat.common.dto.*;
import com.mboaeat.customer.domain.*;

import java.util.Objects;

import static com.mboaeat.common.dto.AddressTypeInfo.*;

public class ServiceUtils {

    private ServiceUtils() {
    }

    public static Client toClient(Customer customer) {
        return Client
                .builder()
                .id(Objects.toString(customer.getId()))
                .name(toName(customer))
                .address(
                        toAddressDto(customer)
                )
                .build();
    }

    private static AddressDTO toAddressDto(Customer customer) {
        if (customer.getAddress() == null) {
            return null;
        }
        AddressDTO.AddressDTOBuilder<?, ?> addressDTOBuilder;
        if (customer.getAddress() instanceof ShippingAddress){
            addressDTOBuilder = ShippingAddressInfo.builder();
        } else {
            addressDTOBuilder = BillingAddressInfo.builder();
            ((BillingAddressInfo.BillingAddressInfoBuilder) addressDTOBuilder).email(email(customer));
            ((BillingAddressInfo.BillingAddressInfoBuilder) addressDTOBuilder).fixedNumber(customer.getFixedPhoneNumber() != null ? customer.getFixedPhoneNumber().getValue() : null);
        }
        toAddressStreet(addressDTOBuilder, customer);
        addressDTOBuilder
                .type(addressType(customer.getAddress()))
                .country(customer.getAddress().getCountry())
                .mobileNumber(customer.getAddress().getMobilePhoneNumber() != null ? customer.getAddress().getMobilePhoneNumber().getValue() : null)
                .name(customer.getAddress().getPersonAddressName() != null ? customer.getAddress().getPersonAddressName().getName() : null)
                .lastName(customer.getAddress().getPersonAddressName() != null ? customer.getAddress().getPersonAddressName().getFirstName() : null)
                .id(Objects.toString(customer.getAddress().getId()));
         return addressDTOBuilder.build();
    }

    private static void toAddressStreet(AddressDTO.AddressDTOBuilder<?, ?> addressDTOBuilder, Customer customer) {
        if (customer.getAddress().getAddressStreet() != null) {
            addressDTOBuilder
                    .city(customer.getAddress().getAddressStreet().getCity())
                    .postBox(customer.getAddress().getAddressStreet().getPostBox())
                    .streetInfo(
                            StreetInfo
                                    .builder()
                                    .streetDescOne(customer.getAddress().getAddressStreet().getStreetDescOne())
                                    .streetDescTwo(customer.getAddress().getAddressStreet().getStreetDescTwo())
                                    .build()
                    );
        }
    }

    private static String toName(Customer customer) {
        if (customer.getClientName() == null) {
            return null;
        }
        return customer.getClientName().getName();
    }

    private static String email(Customer customer) {
        return addressType(customer.getAddress()) == BILLING ?
                ((BillingAddress) customer.getAddress()).getEmailAddress().getValue() : null;
    }

    private static AddressTypeInfo addressType(Address address) {
        return (address instanceof BillingAddress) ? BILLING : SHIPPING;
    }


    public static Customer toCustomer(Client client, Long user) {
        Customer.CustomerBuilder builder = Customer.builder();

                builder
                        .user(user)
                .id(client.asLongId())
                .clientName(
                        ClientName
                                .builder()
                                .name(client.getName())
                                .build()
                )
                .address(
                        toAddress(client.getAddress()));
                if (client.getAddress() instanceof BillingAddressInfo){
                     builder.fixedPhoneNumber(
                             FixedPhoneNumber.builder().value(
                                     ((BillingAddressInfo) client.getAddress()).getFixedNumber()).build()
                     );
                }
            builder.id(client.asLongId());
            return builder.build();
    }

    private static Address toAddress(AddressDTO addressDTO) {
        AddressTypeInfo addressTypeInfo = addressDTO.getType();

        Address.AddressBuilder<?, ?> address = null;

        switch (addressTypeInfo) {
            case BILLING:
                address = BillingAddress
                        .builder()
                        .emailAddress(
                                EmailAddress.builder().value(
                                        ((BillingAddressInfo) addressDTO).getEmail()).build()
                        )
                        .addressStreet(
                                AddressStreet
                                        .builder()
                                        .city(addressDTO.getCity())
                                        .postBox(addressDTO.getPostBox())
                                        .streetDescOne(addressDTO.getStreetInfo().getStreetDescOne())
                                        .streetDescTwo(addressDTO.getStreetInfo().getStreetDescTwo())
                                        .build())

                        .personAddressName(
                                PersonAddressName
                                        .builder()
                                        .name(addressDTO.getName())
                                        .firstName(addressDTO.getLastName())
                                        .build())
                        .mobilePhoneNumber(MobilePhoneNumber.builder().value(addressDTO.getMobileNumber()).build());
                break;
            case SHIPPING:
                address = ShippingAddress
                        .builder()
                        .addressStreet(AddressStreet
                                .builder()
                                .city(addressDTO.getCity())
                                .postBox(addressDTO.getPostBox())
                                .streetDescOne(addressDTO.getStreetInfo() != null ? addressDTO.getStreetInfo().getStreetDescOne() : null)
                                .streetDescTwo(addressDTO.getStreetInfo() != null ? addressDTO.getStreetInfo().getStreetDescTwo() : null)
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

        }
        address.country(addressDTO.getCountry());
        address.id(addressDTO.asLongId());
        return address.build();
    }
}
