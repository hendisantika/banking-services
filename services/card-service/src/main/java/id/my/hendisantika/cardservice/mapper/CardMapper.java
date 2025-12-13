package id.my.hendisantika.cardservice.mapper;

import id.my.hendisantika.cardservice.dto.CardDto;
import id.my.hendisantika.cardservice.entity.Card;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 18.18
 * To change this template use File | Settings | File Templates.
 */
public class CardMapper {
    public static Card toEntity(CardDto cardDto) {
        Card card = new Card();
        card.setCardNumber(String.valueOf(cardDto.getCardNumber()));
        card.setCardType(cardDto.getCardType());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        return card;
    }

    public static CardDto toDto(Card card) {
        return CardDto.builder()
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .availableAmount(card.getAvailableAmount())
                .mobileNumber(card.getMobileNumber())
                .totalLimit(card.getTotalLimit())
                .amountUsed(card.getAmountUsed())
                .build();
    }
}
