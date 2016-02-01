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
package org.talend.hadoop.distribution.cdh550.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh550.CDH550Constant;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.PigConstant;

public class CDH550PigModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        ComponentCondition hbaseLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                PigConstant.HBASE_LOADER_VALUE, EqualityOperator.EQ));
        ComponentCondition parquetLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, PigConstant.PARQUET_LOADER_VALUE, EqualityOperator.EQ));
        ComponentCondition hcatLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                PigConstant.HCAT_LOADER_VALUE, EqualityOperator.EQ));
        ComponentCondition avroLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                PigConstant.AVRO_LOADER_VALUE, EqualityOperator.EQ));
        ComponentCondition rcfileLoaderCondition = new SimpleComponentCondition(new BasicExpression(PigConstant.LOADER_PARAMETER,
                PigConstant.RCFILE_LOADER_VALUE, EqualityOperator.EQ));
        ComponentCondition sequencefileLoaderCondition = new SimpleComponentCondition(new BasicExpression(
                PigConstant.LOADER_PARAMETER, PigConstant.SEQUENCEFILE_LOADER_VALUE, EqualityOperator.EQ));

        Set<DistributionModuleGroup> hs = new HashSet<>();
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(CDH550Constant.HDFS_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(CDH550Constant.MAPREDUCE_MODULE_GROUP.getModuleName()));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_HCATALOG_MODULE_GROUP.getModuleName(), false, hcatLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.HBASE_MODULE_GROUP.getModuleName(), false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_HBASE_MODULE_GROUP.getModuleName(), false, hbaseLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_PARQUET_MODULE_GROUP.getModuleName(), false, parquetLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_AVRO_MODULE_GROUP.getModuleName(), false, avroLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_RCFILE_MODULE_GROUP.getModuleName(), false, rcfileLoaderCondition));
        hs.add(new DistributionModuleGroup(CDH550Constant.PIG_SEQUENCEFILE_MODULE_GROUP.getModuleName(), false,
                sequencefileLoaderCondition));
        return hs;
    }

}