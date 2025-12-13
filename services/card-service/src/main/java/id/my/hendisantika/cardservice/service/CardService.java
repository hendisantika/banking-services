package id.my.hendisantika.cardservice.service;

import id.my.hendisantika.cardservice.common.CardsConstants;
import id.my.hendisantika.cardservice.dto.CardDto;
import id.my.hendisantika.cardservice.entity.Card;
import id.my.hendisantika.cardservice.exception.CardAlreadyExistsException;
import id.my.hendisantika.cardservice.exception.ResourceNotFoundException;
import id.my.hendisantika.cardservice.mapper.CardMapper;
import id.my.hendisantika.cardservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Project : banking-services
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 13/12/25
 * Time: 18.19
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardsRepository;

    public void createCard(String mobileNumber) {
        Optional<Card> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }


    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }


    public CardDto fetchCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardMapper.toDto(card);
    }

    public boolean updateCard(CardDto cardDto) {
        Card card = cardsRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        CardMapper.toEntity(cardDto);
        cardsRepository.save(card);
        return true;
    }


    public boolean deleteCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getCardId());
        return true;
    }
}
