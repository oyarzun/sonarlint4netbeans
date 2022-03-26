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
package com.github.philippefichet.sonarlint4netbeans.ui;

import com.github.philippefichet.sonarlint4netbeans.SonarLintAnalyzerCancelableTask;
import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.TabbedPaneFactory;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//com.github.philippefichet.sonarlint4netbeans//SonarLintAnalyzerAction//EN",
    autostore = false
)
@TopComponent.Description(
    preferredID = "SonarLintAnalyzerActionTopComponent",
    persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_SonarLintAnalyzerActionAction",
    preferredID = "SonarLintAnalyzerActionTopComponent"
)
@Messages({
    "CTL_SonarLintAnalyzerActionAction=SonarLint Analyzer",
    "CTL_SonarLintAnalyzerActionTopComponent=SonarLint Analyzer Window",
    "HINT_SonarLintAnalyzerActionTopComponent=This is a SonarLint Analyzerwindow"
})
public final class SonarLintAnalyzerActionTopComponent extends TopComponent {

    private static final Logger LOG = Logger.getLogger(SonarLintAnalyzerActionTopComponent.class.getName());
    private final RequestProcessor rp = new RequestProcessor(SonarLintAnalyzerActionTopComponent.class);
    private final JTabbedPane tabs;

    public SonarLintAnalyzerActionTopComponent() {
        initComponents();
        setName(Bundle.CTL_SonarLintAnalyzerActionTopComponent());
        setToolTipText(Bundle.HINT_SonarLintAnalyzerActionTopComponent());
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);
        tabs = TabbedPaneFactory.createCloseButtonTabbedPane();
        tabs.setMinimumSize(new Dimension(0, 0));
        tabs.addPropertyChangeListener((java.beans.PropertyChangeEvent evt) -> {
            if (TabbedPaneFactory.PROP_CLOSE.equals(evt.getPropertyName())) {
                SonarLintAnalyzerOutlineContainer container = (SonarLintAnalyzerOutlineContainer) evt.getNewValue();
                tabs.remove(container);
            }
        });
        add(tabs);
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

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    public void addDeepSonarLintAnalyze(Node[] nodes)
    {
        StringBuilder tabTitleBuilder = new StringBuilder();
        for (Node node : nodes) {
            tabTitleBuilder.append(node.getDisplayName())
                .append(", ");
        }
        // Remove ", "
        tabTitleBuilder.delete(tabTitleBuilder.length() - 2, tabTitleBuilder.length());

        SonarLintAnalyzerOutlineContainer sonarLintAnalyzerContainer = new SonarLintAnalyzerOutlineContainer();
        tabs.addTab(
            tabTitleBuilder.toString(),
            sonarLintAnalyzerContainer
        );
        tabs.setSelectedComponent(sonarLintAnalyzerContainer);
        rp.post(() -> {
            SonarLintAnalyzerCancelableTask sonarLintAnalyzerCancelableTask = new SonarLintAnalyzerCancelableTask(sonarLintAnalyzerContainer, nodes);
            sonarLintAnalyzerCancelableTask.run();
        });
    }
}
