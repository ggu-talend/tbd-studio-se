// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.apache100;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;

public class Apache100Distribution extends AbstractDistribution implements HDFSComponent, MRComponent, HBaseComponent,
        PigComponent, HiveComponent {

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions = new HashMap<>();

    private static Map<ComponentType, String> customVersionDisplayNames = new HashMap<>();

    static {
        moduleGroups = new HashMap<>();

        ComponentCondition c1 = new SimpleComponentCondition(new BasicExpression(Constant.PIG_STORE_PARAMETER,
                EqualityOperator.NOT_EQ, Constant.PIG_HCATSTORER_PARAMETER));
        displayConditions.put(ComponentType.PIGOUTPUT, c1);

        customVersionDisplayNames.put(ComponentType.PIGOUTPUT, Constant.PIG_APACHE100_DISPLAY);
        customVersionDisplayNames.put(ComponentType.HIVE, Constant.HIVE_APACHE100_DISPLAY);
    }

    @Override
    public String getDistribution() {
        return EHadoopDistributions.APACHE.getName();
    }

    @Override
    public String getDistributionName() {
        return EHadoopDistributions.APACHE.getDisplayName();
    }

    @Override
    public String getVersion() {
        return EHadoopVersion4Drivers.APACHE_1_0_0.getVersionValue();
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : EHadoopVersion4Drivers.APACHE_1_0_0.getVersionDisplay();
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_1;
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        return false;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public boolean doSupportHCatalog() {
        return false;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        // return false because this distribution doesn't support HCatalog.
        return false;
    }

    @Override
    public boolean doSupportHBase() {
        return true;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportEmbeddedMode() {
        return true;
    }

    @Override
    public boolean doSupportStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportHive1() {
        return true;
    }

    @Override
    public boolean doSupportHive2() {
        return false;
    }

    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return true;
    }

    @Override
    public boolean doSupportSSL() {
        return false;
    }

    @Override
    public boolean doSupportORCFormat() {
        return false;
    }

    @Override
    public boolean doSupportAvroFormat() {
        return false;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return false;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        return false;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

}
