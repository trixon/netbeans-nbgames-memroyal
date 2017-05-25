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

import org.nbgames.core.api.ui.OptionsPanel;
import se.trixon.almond.nbp.dialogs.ColorChooserDialog;

/**
 *
 * @author Patrik Karlsson
 */
final class OptionPanel extends OptionsPanel {

    private final Options mOptions = Options.INSTANCE;

    public OptionPanel() {
        initComponents();
        load();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundButton = new se.trixon.almond.nbp.swing.ColorChooserButton();

        org.openide.awt.Mnemonics.setLocalizedText(backgroundButton, org.openide.util.NbBundle.getMessage(OptionPanel.class, "OptionPanel.backgroundButton.text")); // NOI18N
        backgroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backgroundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundButtonActionPerformed
        backgroundButton.setColor(ColorChooserDialog.showDialog(backgroundButton.getColor()));
    }//GEN-LAST:event_backgroundButtonActionPerformed

    @Override
    public void load() {
        backgroundButton.setColor(mOptions.getColorBackground());
    }

    void store() {
        mOptions.setColorBackground(backgroundButton.getColor());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.trixon.almond.nbp.swing.ColorChooserButton backgroundButton;
    // End of variables declaration//GEN-END:variables
}
