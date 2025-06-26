package com.cbf.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Dynamic DataSource
 *
 * @author Frank
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        // set default data source.
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        // set data source map, store multi type of data source.
        super.setTargetDataSources(targetDataSources);
        // initialize configuration.
        super.afterPropertiesSet();
    }

    /**
     * Determine the current data source.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}