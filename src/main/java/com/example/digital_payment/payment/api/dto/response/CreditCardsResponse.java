package com.example.digital_payment.payment.api.dto.response;

import java.util.List;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public record CreditCardsResponse(List<CreditCards> cards) {

}
