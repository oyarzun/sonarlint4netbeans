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
package com.github.philippefichet.sonarlint4netbeans.treenode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.sonarsource.sonarlint.core.client.api.common.analysis.DefaultClientIssue;

/**
 *
 * @author FICHET Philippe &lt;philippe.fichet@laposte.net&gt;
 */
public class SonarLintAnalyserIssueSeverityRuleKeyChildren extends Children.Keys<String> {

    private final java.util.Map<String, SonarLintAnalyserIssueSeverityRuleKeyNode> nodeInstancies = new HashMap<>();


    public SonarLintAnalyserIssueSeverityRuleKeyChildren() {

    }

    public void addIssue(DefaultClientIssue issue, String ruleName) {
        String ruleKey = issue.getRuleKey();
        nodeInstancies.computeIfAbsent(ruleKey, (k) -> new SonarLintAnalyserIssueSeverityRuleKeyNode(issue, ruleName))
            .addIssue(issue, ruleName);
        ArrayList<String> keys = new ArrayList<>(nodeInstancies.keySet());
        Collections.sort(keys);
        setKeys(keys);
    }

    
    @Override
    protected Node[] createNodes(String ruleKey) {
        return new Node[] {
            nodeInstancies.get(ruleKey)
        };
    }
}
