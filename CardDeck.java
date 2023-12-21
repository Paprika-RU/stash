import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        CardDeck deck = new CardDeck(); // Создаем экземпляр колоды карт
        deck.shuffle(); // Перемешиваем колоду

        Card card1 = deck.dealCard(); // Берем карту из колоды
        System.out.println("Выдана карта: " + card1);
        System.out.println("Размер колоды: " + deck.getSize());

        Card card2 = deck.dealCard(); // Берем еще одну карту из колоды
        System.out.println("Выдана карта: " + card2);
        System.out.println("Размер колоды: " + deck.getSize());

        deck.returnCard(card1); // Возвращаем первую карту в колоду
        System.out.println("Карта возвращена в колоду");
        System.out.println("Размер колоды: " + deck.getSize());
    }
}

class CardDeck {
    private List<Card> deck; // Список карт в колоде

    public CardDeck() {
        deck = new ArrayList<>(); // Создаем пустую колоду
        initializeDeck(); // Инициализируем колоду картами
    }

    private void initializeDeck() {
        for (Card.Suit suit : Card.Suit.values()) { // Проходим по всем мастям
            for (Card.Rank rank : Card.Rank.values()) { // Проходим по всем достоинствам
                deck.add(new Card(rank, suit)); // Создаем карту и добавляем в колоду
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck); // Перемешиваем колоду
    }

    public Card dealCard() {
        if (deck.isEmpty()) {
            return null; // Если колода пуста, возвращаем null
        }
        return deck.remove(deck.size() - 1); // Берем карту из конца колоды и возвращаем ее
    }

    public void returnCard(Card card) {
        deck.add(card); // Возвращаем карту в колоду
    }

    public int getSize() {
        return deck.size(); // Возвращаем размер колоды
    }
}

class Card {
    enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
