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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.nbgames.core.card.CardDeckManager;
import org.nbgames.core.card.CardPath;
import org.nbgames.core.card.PlayingCard.Side;
import org.openide.util.NbPreferences;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GameCardList extends LinkedList<GameCard> {

    protected CardPath[] mCardPaths = new CardPath[2];

    public GameCardList() {
        init();
    }

    public void applyTheme(CardPath aCardPath) {
    }

    public void applyTheme(CardPath[] cardPaths) {

        for (Iterator<GameCard> it = this.iterator(); it.hasNext();) {
            GameCard gameCard = it.next();
//        for (GameCard gameCard : this) {

            gameCard.getPlayingCard().setPathBack(cardPaths[gameCard.getDeckIndex()].getPath(Side.BACK));
            gameCard.getPlayingCard().setPathFront(cardPaths[gameCard.getDeckIndex()].getPath(Side.FRONT));

            Side side = Side.FRONT;
            if (gameCard.mIsSelected == false) {
                side = Side.BACK;
            }
            gameCard.setIcon(gameCard.getPlayingCard().getImage(side));
        }
    }

    public void shuffle() {
        Collections.shuffle(this);
    }

    private void init() {
        Preferences preferences = NbPreferences.forModule(org.nbgames.core.card.OptionPanel.class);

        mCardPaths[0] = new CardPath(preferences.get("front0", CardDeckManager.DEFAULT_PATH_FRONT_0), preferences.get("back0", CardDeckManager.DEFAULT_PATH_BACK_0));
        mCardPaths[1] = new CardPath(preferences.get("front1", CardDeckManager.DEFAULT_PATH_FRONT_1), preferences.get("back1", CardDeckManager.DEFAULT_PATH_BACK_1));
        applyTheme(mCardPaths);

        preferences.addPreferenceChangeListener(new PreferenceChangeListener() {

            @Override
            public void preferenceChange(PreferenceChangeEvent evt) {
                if (evt.getKey().equals("front0")) {
                    mCardPaths[0].setPath(Side.FRONT, evt.getNewValue());
                }
                if (evt.getKey().equals("front1")) {
                    mCardPaths[1].setPath(Side.FRONT, evt.getNewValue());
                }

                if (evt.getKey().equals("back0")) {
                    mCardPaths[0].setPath(Side.BACK, evt.getNewValue());
                }
                if (evt.getKey().equals("back1")) {
                    mCardPaths[1].setPath(Side.BACK, evt.getNewValue());
                }
                applyTheme(mCardPaths);
            }
        });
    }
}
