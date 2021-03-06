/*
 * Copyright 2017 Patrik Karlsson.
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

import org.nbgames.core.api.GameCategory;
import org.nbgames.core.api.GameController;
import org.nbgames.core.api.ui.GamePanel;
import org.nbgames.core.api.ui.NbgOptionsPanel;
import org.nbgames.core.api.ui.NewGamePanel;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Patrik Karlsson
 */
@ServiceProviders(value = {
    @ServiceProvider(service = GameController.class)
})
public class Memroyal extends GameController {

    public static final String TAG = "Memroyal";
    private MemroyalPanel mGamePanel;
    private MemroyalNewGamePanel mNewGamePanel;
    private OptionPanel mOptionPanel;

    public Memroyal() {
    }

    @Override
    public GameCategory getCategory() {
        return GameCategory.CARD;
    }

    @Override
    public String getHelp() {
        return getHelp(Memroyal.class);
    }

    @Override
    public NewGamePanel getNewGamePanel() {
        if (mNewGamePanel == null) {
            mNewGamePanel = new MemroyalNewGamePanel();
        }

        return mNewGamePanel;
    }

    @Override
    public NbgOptionsPanel getOptionsPanel() {
        if (mOptionPanel == null) {
            mOptionPanel = new OptionPanel();
        }

        return mOptionPanel;
    }

    @Override
    public GamePanel getPanel() {
        if (mGamePanel == null) {
            mGamePanel = new MemroyalPanel();
        }

        return mGamePanel;
    }

    @Override
    public void onRequestNewGameStart() {
        getPanel().newGame();
    }
}
