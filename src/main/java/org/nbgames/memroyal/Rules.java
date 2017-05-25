/* 
 * Copyright 2015 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nbgames.memroyal;

import org.nbgames.core.card.PlayingCard;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Rules {

    private GameCardList mSelectedGameCards;
    private final GameDeck mGameDeck;
    private final Variation mVariation;

    public Rules(GameDeck gameDeck, Variation variation) {
        mGameDeck = gameDeck;
        mVariation = variation;
    }

    protected void parse() {
        mSelectedGameCards = mGameDeck.getSelectedGameCards();

        switch (mVariation) {
            case ONE_DECK_STANDARD:
                parseStandard();
                break;

            case ONE_DECK_ANY_COLOR:
                parseAnyColor();
                break;

            case ONE_DECK_ZEBRA:
                parseZebra();
                break;

            case TWO_DECKS:
                parseTwoDecks();
                break;

            case TWO_DECKS_DUEL:
                parseTwoDecks();
                break;

            case TWO_DECKS_CHECKERBOARD:
                parseTwoDecks();
                break;
        }
    }

    private void parseAnyColor() {
        PlayingCard c1;
        PlayingCard c2;
        int numOfCards = mSelectedGameCards.size();

        if (numOfCards == 2) {
            c1 = mSelectedGameCards.get(0).getPlayingCard();
            c2 = mSelectedGameCards.get(1).getPlayingCard();

            if (c1.equalsValue(c2)) {
                mGameDeck.hideSelected();
            }
        }

        if (numOfCards == 3) {
            mGameDeck.restoreSelected();
        }
    }

    private void parseStandard() {
        PlayingCard c1;
        PlayingCard c2;
        int numOfCards = mSelectedGameCards.size();

        if (numOfCards == 2) {
            c1 = mSelectedGameCards.get(0).getPlayingCard();
            c2 = mSelectedGameCards.get(1).getPlayingCard();

            if ((c1.equalsColor(c2)) && (c1.equalsValue(c2))) {
                mGameDeck.hideSelected();
            }
        }

        if (numOfCards == 3) {
            mGameDeck.restoreSelected();
        }
    }

    private void parseTwoDecks() {
        PlayingCard c1;
        PlayingCard c2;
        int numOfCards = mSelectedGameCards.size();

        if (numOfCards == 2) {
            c1 = mSelectedGameCards.get(0).getPlayingCard();
            c2 = mSelectedGameCards.get(1).getPlayingCard();

            if (c1.equals(c2)) {
                mGameDeck.hideSelected();
            }
        }

        if (numOfCards == 3) {
            mGameDeck.restoreSelected();
        }
    }

    private void parseZebra() {
        PlayingCard c1;
        PlayingCard c2;
        int numOfCards = mSelectedGameCards.size();

        if (numOfCards == 2) {
            c1 = mSelectedGameCards.get(0).getPlayingCard();
            c2 = mSelectedGameCards.get(1).getPlayingCard();

            if ((!c1.equalsColor(c2)) && (c1.equalsValue(c2))) {
                mGameDeck.hideSelected();
            }
        }

        if (numOfCards == 3) {
            mGameDeck.restoreSelected();
        }

    }

    public enum Variation {

        ONE_DECK_STANDARD("CTL_oneDeckStandard"),
        ONE_DECK_ANY_COLOR("CTL_oneDeckAnyColor"),
        ONE_DECK_ZEBRA("CTL_oneDeckZebra"),
        TWO_DECKS("CTL_twoDecksStandard"),
        TWO_DECKS_CHECKERBOARD("CTL_twoDecksCheckerboard"),
        TWO_DECKS_DUEL("CTL_twoDecksDuel");
        private final String mTitle;

        Variation(String title) {
            mTitle = title;
        }

        @Override
        public String toString() {
            return mTitle;
        }
    }
}
