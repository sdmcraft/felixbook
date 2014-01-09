/*
 * Copyright (c) 2013-14, Satya Deep Maheshwari. All rights reserved.
 *
 * The contents of this file are subject to the MIT License
 * You may not use this file except in compliance with the License.
 * A copy of the License is available at
 * http://opensource.org/licenses/MIT
 *
 * Copyright (c) 2013-2014 Satya Deep Maheshwari
 */
package com.packtpub.felix.bookshelf.inventory.impl.h2;

import org.osgi.framework.BundleContext;

import org.osgi.service.jdbc.DataSourceFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
@Component(immediate = true, metatype = true, policy = ConfigurationPolicy.REQUIRE)
@Service
public class H2DataSourceImpl
    implements DataSource
{
    //~ Static variables/initializers ----------------------------------------------------

    @Property
    private static final String PROP_JDBC_USER = "h2.jdbc.user";
    @Property
    private static final String PROP_JDBC_PWD = "h2.jdbc.pwd";

    //~ Instance variables ---------------------------------------------------------------

    DataSource dataSource;
    @Reference(bind = "bindDataSource", policy = ReferencePolicy.DYNAMIC)
    DataSourceFactory dataSourceFactory;
    Properties properties = new Properties();

    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param context DOCUMENT ME!
     * @param config DOCUMENT ME!
     */
    @Activate
    public void activate(
        BundleContext       context,
        Map<String, Object> config)
    {
        properties.setProperty("JDBC_USER", (String) config.get(PROP_JDBC_USER));
        properties.setProperty("JDBC_PASSWORD", (String) config.get(PROP_JDBC_PWD));
    }


    /**
     * DOCUMENT ME!
     *
     * @param dataSourceFactory DOCUMENT ME!
     *
     * @throws SQLException
     */
    public void bindDataSource(DataSourceFactory dataSourceFactory)
      throws SQLException
    {
        System.out.println("DataSourceFactory bound:" + dataSourceFactory);
        this.dataSourceFactory = dataSourceFactory;
        this.dataSource = dataSourceFactory.createDataSource(properties);
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public Connection getConnection()
      throws SQLException
    {
        return dataSource.getConnection();
    }


    /**
     * DOCUMENT ME!
     *
     * @param username DOCUMENT ME!
     * @param password DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public Connection getConnection(
        String username,
        String password)
      throws SQLException
    {
        return dataSource.getConnection(username, password);
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public PrintWriter getLogWriter()
      throws SQLException
    {
        return dataSource.getLogWriter();
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public int getLoginTimeout()
      throws SQLException
    {
        return dataSource.getLoginTimeout();
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLFeatureNotSupportedException DOCUMENT ME!
     */
    public Logger getParentLogger()
      throws SQLFeatureNotSupportedException
    {
        return dataSource.getParentLogger();
    }


    /**
     * DOCUMENT ME!
     *
     * @param iface DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public boolean isWrapperFor(Class<?> iface)
      throws SQLException
    {
        return dataSource.isWrapperFor(iface);
    }


    /**
     * DOCUMENT ME!
     *
     * @param out DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public void setLogWriter(PrintWriter out)
      throws SQLException
    {
        dataSource.setLogWriter(out);
    }


    /**
     * DOCUMENT ME!
     *
     * @param seconds DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public void setLoginTimeout(int seconds)
      throws SQLException
    {
        dataSource.setLoginTimeout(seconds);
    }


    /**
     * DOCUMENT ME!
     *
     * @param <T> DOCUMENT ME!
     * @param iface DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws SQLException DOCUMENT ME!
     */
    public <T> T unwrap(Class<T> iface)
      throws SQLException
    {
        return dataSource.unwrap(iface);
    }
}
