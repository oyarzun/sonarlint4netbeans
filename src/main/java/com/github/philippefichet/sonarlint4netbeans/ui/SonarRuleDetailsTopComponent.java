/*
 * sonarlint4netbeans: SonarLint integration for Apache Netbeans
 * Copyright (C) 2020 Philippe FICHET.
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
package com.github.philippefichet.sonarlint4netbeans.ui;

import com.github.philippefichet.sonarlint4netbeans.SonarLintDataManager;
import com.github.philippefichet.sonarlint4netbeans.SonarLintDataManagerUtils;
import com.github.philippefichet.sonarlint4netbeans.SonarLintEngine;
import com.github.philippefichet.sonarlint4netbeans.project.SonarLintProjectPreferenceScope;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;
import javax.swing.JTabbedPane;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.TabbedPaneFactory;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 * @author FICHET Philippe &lt;philippe.fichet@laposte.net&gt;
 */
@ConvertAsProperties(
    dtd = "-//com.github.philippefichet.sonarlint4netbeans//SonarRuleDetails//EN",
    autostore = false
)
@TopComponent.Description(
    preferredID = "SonarRuleDetailsTopComponent",
    iconBase = "com/github/philippefichet/sonarlint4netbeans/resources/sonarlint.png",
    persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "com.github.philippefichet.sonarlint4netbeans.SonarRuleDetailsTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_SonarRuleDetailsAction",
    preferredID = "SonarRuleDetailsTopComponent"
)
@Messages({
    "CTL_SonarRuleDetailsAction=Sonar Rule Details",
    "CTL_SonarRuleDetailsTopComponent=Sonar Rule Details Window",
    "HINT_SonarRuleDetailsTopComponent=This is a Sonar Rule Details window"
})
public final class SonarRuleDetailsTopComponent extends TopComponent {
    private final SonarLintEngine sonarLintEngine;
    private final SonarLintDataManager sonarLintDataManager;
    private final JTabbedPane tabs;
    public SonarRuleDetailsTopComponent() {
        sonarLintEngine = Lookup.getDefault().lookup(SonarLintEngine.class);
        sonarLintDataManager = Lookup.getDefault().lookup(SonarLintDataManager.class);
        initComponents();
        setName(Bundle.CTL_SonarRuleDetailsTopComponent());
        setToolTipText(Bundle.HINT_SonarRuleDetailsTopComponent());
        tabs = TabbedPaneFactory.createCloseButtonTabbedPane();
        tabs.setMinimumSize(new Dimension(0, 0));
        tabs.addPropertyChangeListener((java.beans.PropertyChangeEvent evt) -> {
            if (TabbedPaneFactory.PROP_CLOSE.equals(evt.getPropertyName())) {
                SonarRuleDetailsPanel container = (SonarRuleDetailsPanel) evt.getNewValue();
                tabs.remove(container);
            }
        });
        add(tabs, BorderLayout.CENTER);
        getOrCreateAndAddSonarRuleDetailsPanelFromProject(SonarLintEngine.GLOBAL_SETTINGS_PROJECT);
    }

    @Override
    public void open() {
        super.open();
        getOrCreateAndAddSonarRuleDetailsPanelFromProject(SonarLintEngine.GLOBAL_SETTINGS_PROJECT);
    }

    public void open(Project project) {
        super.open();
        getOrCreateAndAddSonarRuleDetailsPanelFromProject(project);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /**
     * Require by @ConvertAsProperties
     * @param p properties instance used to add custom properties
     */
    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    /**
     * Require by @ConvertAsProperties
     * @param p properties instance used to read custom properties
     */
    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    public void setSonarRuleKeyFilter(String sonarRuleKeyFilter, Project project)
    {
        SonarRuleDetailsPanel sonarRuleDetailsPanel = getOrCreateAndAddSonarRuleDetailsPanelFromProject(project);
        sonarRuleDetailsPanel.setSonarRuleKeyFilter(sonarRuleKeyFilter);
    }
    
    private SonarRuleDetailsPanel getOrCreateAndAddSonarRuleDetailsPanelFromProject(Project project)
    {
        Project searchedProject = SonarLintDataManagerUtils.getProjectForAnalyse(sonarLintDataManager, project);
        for (Component component : tabs.getComponents()) {
            if (component instanceof SonarRuleDetailsPanel) {
                SonarRuleDetailsPanel sonarRuleDetailsPanel = (SonarRuleDetailsPanel) component;
                if (Objects.equals(sonarRuleDetailsPanel.getProject(), searchedProject)) {
                    tabs.setSelectedComponent(sonarRuleDetailsPanel);
                    return sonarRuleDetailsPanel;
                }
            }
        }
        
        SonarRuleDetailsPanel sonarRuleDetailsPanel = new SonarRuleDetailsPanel(sonarLintEngine, searchedProject);
        
        String title = 
            sonarLintDataManager.getPreferencesScope(project) == SonarLintProjectPreferenceScope.PROJECT
            ? ProjectUtils.getInformation(searchedProject).getDisplayName()
            : org.openide.util.NbBundle.getMessage(SonarRuleDetailsTopComponent.class, "SonarRuleDetailsTopComponent.globalSettingsProject");

        tabs.addTab(title, sonarRuleDetailsPanel);
        return sonarRuleDetailsPanel;
    }
}
