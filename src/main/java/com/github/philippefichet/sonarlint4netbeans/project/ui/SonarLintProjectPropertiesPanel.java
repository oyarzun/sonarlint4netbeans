/*
 * sonarlint4netbeans: SonarLint integration for Apache Netbeans
 * Copyright (C) 2022 Philippe FICHET.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.github.philippefichet.sonarlint4netbeans.project.ui;

import com.github.philippefichet.sonarlint4netbeans.SonarLintEngine;
import com.github.philippefichet.sonarlint4netbeans.ui.SonarLintOptionsPanelProperties;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;

/**
 *
 * @author FICHET Philippe &lt;philippe.fichet@laposte.net&gt;
 */
public class SonarLintProjectPropertiesPanel extends javax.swing.JPanel {
    private final Map<String, String> extraProperties = new HashMap<>();
    /**
     * Creates new form SonarLintProjectPropertiesPanel
     */
    public SonarLintProjectPropertiesPanel(SonarLintEngine sonarLintEngine, Project project, ProjectCustomizer.Category category) {
        category.setOkButtonListener((ActionEvent e) -> 
            sonarLintEngine.setAllExtraProperties(extraProperties, project)
        );
        initComponents();
        SonarLintOptionsPanelProperties sonarLintOptionsPanelProperties = new SonarLintOptionsPanelProperties(
            sonarLintEngine.getAllExtraProperties(project), 
            (Map<String, String> newExtraProperties) -> {
                extraProperties.clear();
                extraProperties.putAll(newExtraProperties);
            }
        );
        add(sonarLintOptionsPanelProperties, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
