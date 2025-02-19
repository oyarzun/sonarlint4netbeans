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
package com.github.philippefichet.sonarlint4netbeans.ui.listener;

/**
 * Interface to notify when a rule parameter changed in SonarLintRuleSettings
 * @author FICHET Philippe &lt;philippe.fichet@laposte.net&gt;
 */
@FunctionalInterface
public interface SonarLintRuleSettingsListener {

    /**
     * Call when a rule parameter value change
     * @param ruleKey rule
     * @param parameterName parameter name changed
     * @param parameterValue new value of parameter
     */
    public void ruleParameterValueChange(String ruleKey, String parameterName, String parameterValue);
}
