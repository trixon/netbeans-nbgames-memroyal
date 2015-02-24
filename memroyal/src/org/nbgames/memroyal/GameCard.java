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

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import org.nbgames.core.card.PlayingCard;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GameCard extends JLabel implements Cloneable {

    public static final Dimension IMAGE = new Dimension(72, 98);
    public static final int MARGIN_H = 14;
    public static final int MARGIN_V = 12;
    public static final Dimension DIMENSION = new Dimension(IMAGE.width + MARGIN_H, IMAGE.height + MARGIN_V);
    public boolean mIsSelected = false;
    private int mDeckIndex;
    private GameDeck mGameDeck;
    private boolean mIsLocked = false;
    private PlayingCard mPlayingCard;

    public GameCard() {
        init();
    }

    public GameCard(GameDeck gameDeck, PlayingCard playingCard) {
        mGameDeck = gameDeck;
        mPlayingCard = playingCard;
        init();
    }

    @Override
    public GameCard clone() {
        try {
            return (GameCard) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public int getDeckIndex() {
        return mDeckIndex;
    }

    public PlayingCard getPlayingCard() {
        return mPlayingCard;
    }

    public void randomizeLocation() {
        Random generator = new Random();

        int top;
        int left;
        top = generator.nextInt(GameCard.MARGIN_V - 2) + 2;
        left = generator.nextInt(GameCard.MARGIN_H - 2) + 2;
        setBorder(new EmptyBorder(top, left, 0, 0));
    }

    public void reset() {
        mIsSelected = false;
        mIsLocked = false;
        setIcon(mPlayingCard.getImage(PlayingCard.Side.BACK));
        setVisible(false);
    }

    public void select() {
        mIsSelected = true;
        setIcon(mPlayingCard.getImage(PlayingCard.Side.FRONT));
        mGameDeck.getSelectedGameCards().add(this);
        mGameDeck.notifyObservable();
        randomizeLocation();
    }

    public void setDeckIndex(int deckIndex) {
        mDeckIndex = deckIndex;
    }

    protected void pairedOut() {
        setVisible(false);
        mIsLocked = true;
    }

    protected void restore() {
        mIsSelected = false;
        setIcon(mPlayingCard.getImage(PlayingCard.Side.BACK));
        randomizeLocation();
    }

    private void init() {
        setOpaque(false);
        setLayout(null);
        setPreferredSize(DIMENSION);
        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    if ((evt.getX() >= getInsets().left) && (evt.getX() <= getInsets().left + IMAGE.width)) {
                        if ((evt.getY() >= getInsets().top) && (evt.getY() <= getInsets().top + IMAGE.height)) {
                            if ((mIsSelected == false) && (mIsLocked == false) && (mGameDeck.mTimerHide.isRunning() == false)) {
                                select();
                            }
                        }
                    }
                }
            }
        });
    }
}
