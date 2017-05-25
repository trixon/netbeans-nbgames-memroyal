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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observer;
import javax.swing.JLabel;
import org.nbgames.core.card.CardDeck;
import org.nbgames.core.card.PlayingCard;
import org.nbgames.core.card.PlayingCard.Suit;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GameDeck extends GameCardList {

    protected javax.swing.Timer mTimerHide;
    private CardDeck mCardDeck1;
    private CardDeck mCardDeck2;
    private GameDeckObservable mGameDeckObservable = new GameDeckObservable();
    private ActionListener mHideActionListener;
    private int mLevel;
    private LinkedList<Integer> mLevelList = new LinkedList<Integer>();
    private GameCardList mRemovedGameCardList = new GameCardList();
    private int mScore;
    private GameCardList mSelectedGameCardList = new GameCardList();
    private ActionListener mShowActionListener;
    private GameCardList mTempGameCardList = new GameCardList();
    private javax.swing.Timer mTimerShow;

    public GameDeck(Mode mode, int level) {
        mLevel = level;
        init();

        switch (mode) {

            case ONE_DECK:
                initOneDeck();
                break;

            case TWO_DECKS:
                initTwoDecks();
                break;

            case TWO_DECKS_CHECKER:
                initTwoDecksChecker();
                break;

            case TWO_DECKS_DUEL:
                initTwoDecksDuel();
                break;
        }

        mRemovedGameCardList.clear();
        mSelectedGameCardList.clear();

        while (!mTempGameCardList.isEmpty()) {
            add(mTempGameCardList.pop());
        }
    }

    public void applyDeckTheme() {
        applyTheme(mCardPaths);
    }

    public GameCardList getSelectedGameCards() {
        return mSelectedGameCardList;
    }

    public void reset() {
        for (GameCard gameCard : this) {
            gameCard.reset();
        }
        mRemovedGameCardList.clear();
        mSelectedGameCardList.clear();
        shuffle();
        mScore = 0;
    }

    public void setObserver(Observer o) {
        mGameDeckObservable.addObserver(o);
    }

    protected void hideSelected() {
        mTimerHide.start();
    }

    protected void notifyObservable() {
        mGameDeckObservable.notify(GameDeckObservable.GameCardEvent.FLIP);
    }

    protected void restoreSelected() {
        GameCardList deselectGameCards = new GameCardList();
        deselectGameCards = mSelectedGameCardList;
        GameCard mc = deselectGameCards.removeLast();

        for (GameCard gameCard : deselectGameCards) {
            gameCard.restore();
        }

        mSelectedGameCardList.clear();
        mSelectedGameCardList.add(mc);
        mScore++;
    }

    private void init() {

        for (PlayingCard.Value value : PlayingCard.Value.values()) {
            mLevelList.add(value.toInt());
        }

        Collections.shuffle(mLevelList);

        for (int i = 13; i > mLevel; i--) {
            mLevelList.remove();
        }

        mCardDeck1 = new CardDeck();
        mCardDeck2 = new CardDeck();

        initDeck(mCardDeck1);
        initDeck(mCardDeck2);

        mTempGameCardList.clear();
        initTimers();
    }

    private void initDeck(CardDeck cardDeck) {
        cardDeck.clear();

        for (Integer integer : mLevelList) {
            for (Suit s : Suit.values()) {
                cardDeck.add(new PlayingCard(s, PlayingCard.Value.class.getEnumConstants()[integer - 1]));
            }
        }
    }

    private void initOneDeck() {
        initSingle(0);
        mTempGameCardList.shuffle();
    }

    private void initSingle(int backIndex) {
        GameCard gameCard = new GameCard();

        while (!mCardDeck1.isEmpty()) {
            gameCard = new GameCard(this, mCardDeck1.pop());
            gameCard.reset();

            gameCard.setDeckIndex(backIndex);
            mTempGameCardList.add(gameCard);
        }

        initDeck(mCardDeck1);
    }

    private void initTimers() {
        mHideActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timerActionHide();
            }
        };
        mTimerHide = new javax.swing.Timer(0, mHideActionListener);
        mTimerHide.setInitialDelay(500);

        mShowActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timerActionShow();
            }
        };

        mTimerShow = new javax.swing.Timer(15, mShowActionListener);
        mTimerShow.setInitialDelay(100);
    }

    private void initTwoDecks() {
        initSingle(0);
        initSingle(0);
        mTempGameCardList.shuffle();
    }

    private void initTwoDecksChecker() {
        GameCard gameCard = new GameCard();
        mCardDeck1.shuffle();
        mCardDeck2.shuffle();

        while (!mCardDeck1.isEmpty()) {
            gameCard = new GameCard(this, mCardDeck1.pop());
            gameCard.setDeckIndex(0);
            gameCard.reset();
            mTempGameCardList.add(gameCard);

            gameCard = new GameCard(this, mCardDeck2.pop());
            gameCard.setDeckIndex(1);
            gameCard.reset();
            mTempGameCardList.add(gameCard);
        }

        initDeck(mCardDeck1);
        initDeck(mCardDeck2);
    }

    private void initTwoDecksDuel() {
        GameCard gameCard = new GameCard();
        mCardDeck1.shuffle();
        mCardDeck2.shuffle();

        while (!mCardDeck1.isEmpty()) {
            gameCard = new GameCard(this, mCardDeck1.pop());
            gameCard.setDeckIndex(0);
            gameCard.reset();
            mTempGameCardList.add(gameCard);
        }

        while (!mCardDeck2.isEmpty()) {
            gameCard = new GameCard(this, mCardDeck2.pop());
            gameCard.setDeckIndex(1);
            gameCard.reset();
            mTempGameCardList.add(gameCard);
        }

        initDeck(mCardDeck1);
        initDeck(mCardDeck2);
    }

    private void timerActionHide() {
        GameCard gameCard = mSelectedGameCardList.pop();
        gameCard.pairedOut();
        mRemovedGameCardList.add(gameCard);

        if (mSelectedGameCardList.isEmpty()) {
            mTimerHide.stop();
            if (mRemovedGameCardList.size() == this.size()) {
                mTimerShow.start();
            }
        }
    }

    private void timerActionShow() {
        GameCard gameCard = mRemovedGameCardList.pop();
        gameCard.setVisible(true);

        if (mRemovedGameCardList.isEmpty()) {
            mTimerShow.stop();
            JLabel label = new JLabel(String.valueOf(mScore));
            throw new UnsupportedOperationException("Not supported yet.");
//            String result = GameOver.showDialog(label);

//            if (result.equalsIgnoreCase("startNewGame")) {
//                MemroyalTopComponent.findInstance().getGameController().startNewGame();
//            }
        }
    }

    public enum Mode {

        ONE_DECK,
        TWO_DECKS,
        TWO_DECKS_CHECKER,
        TWO_DECKS_DUEL;
    }
}
