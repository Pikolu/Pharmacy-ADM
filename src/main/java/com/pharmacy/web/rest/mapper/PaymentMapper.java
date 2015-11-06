package com.pharmacy.web.rest.mapper;

import com.pharmacy.domain.*;
import com.pharmacy.web.rest.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMapper {

    PaymentDTO paymentToPaymentDTO(Payment payment);

    @Mapping(target = "pharmacys", ignore = true)
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
}
