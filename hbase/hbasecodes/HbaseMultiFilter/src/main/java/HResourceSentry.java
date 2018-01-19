package main.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.security.UserProvider;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HResourceSentry {

    public interface SentryExceptionInterceptor
    {
        void onGetConnectionException(Exception e);

        void onGetUgiException(Exception e);

        void onUgiReloginException(Exception e);
    }

    public interface SentryOperation<T>
    {
        T execute();
    }
    //
    private static final Log LOG = LogFactory.getLog(HResourceSentry.class);
    //
    private final Long intervalInMs;
    private final String keytab;
    private final String principal;
    private final Configuration conf;
    private final SentryExceptionInterceptor callback;
    private final ReadWriteLock lock;
    //
    private UserGroupInformation ugi;
    private Connection connection;
    private Timer timer;
    //
    public HResourceSentry(String keytab, String principal)
    {
        this(keytab, principal, new Configuration());
    }

    public HResourceSentry(String keytab, String principal, Configuration configuration)
    {
        this(keytab, principal, configuration, null);
    }

    public HResourceSentry(String keytab, String principal, SentryExceptionInterceptor callback)
    {
        this(keytab, principal, new Configuration(), callback);
    }

    public HResourceSentry(String keytab, String principal, Configuration configuration,
                           SentryExceptionInterceptor callback)
    {
        this.intervalInMs = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
        this.keytab = keytab;
        this.principal = principal;
        this.conf = HBaseConfiguration.create(configuration);
        this.callback = callback;
        this.lock = new ReentrantReadWriteLock(true);
    }

    public void init() throws Exception
    {
        if (LOG.isInfoEnabled())
        {
            LOG.info("Executing login using principal " + principal + " keytab " + keytab
                    + ", obtaining userGroupInformation");
        }
        UserGroupInformation.loginUserFromKeytab(principal, keytab);
        ugi = UserGroupInformation.getLoginUser();
        User user = new UserProvider().create(ugi);
        if (LOG.isInfoEnabled())
        {
            LOG.info("Login successful, ugi=" + ugi + "; establishing hbase connection using user " + user);
        }

        connection = ConnectionFactory.createConnection(this.conf, user);

        if (LOG.isInfoEnabled())
        {
            LOG.info("Connection established, connection=" + connection + "; spawning relogin timer every " + intervalInMs
                    + "ms");
        }
        timer = new Timer("ReloginTimer", true);
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    try
                    {
                        lock.writeLock().lock();
                        ugi.reloginFromKeytab();
                    } finally
                    {
                        lock.writeLock().unlock();
                    }
                } catch (IOException e)
                {
                    if (callback != null)
                    {
                        callback.onUgiReloginException(e);
                    }
                    throw new IllegalStateException(e);
                }
            }
        }, intervalInMs, intervalInMs);

    }

    public void destroy() throws Exception
    {
        if (LOG.isInfoEnabled())
        {
            LOG.info("Destroying " + this);
        }
        if (connection != null && !connection.isClosed())
        {
            if (LOG.isInfoEnabled())
            {
                LOG.info("Closing hbase connection");
            }
            connection.close();
        }
        if (timer != null)
        {
            if (LOG.isInfoEnabled())
            {
                LOG.info("Canceling timer");
            }
            timer.cancel();
        }
    }

    public Connection getConnection()
    {
        if (connection == null)
        {
            IllegalStateException e = new IllegalStateException(this + " has not been initialized!");
            if (callback != null)
            {
                callback.onGetConnectionException(e);
            }
            throw e;
        }
        return connection;
    }

    public <T> SentryOperation<T> newOperation(final PrivilegedExceptionAction<T> target)
    {
        return new SentryOperation<T>()
        {
            //@Override
            public T execute()
            {
                try
                {
                    lock.readLock().lock();
                    //
                    if (ugi == null)
                    {
                        IllegalStateException e = new IllegalStateException(this + " has not been initialized!");
                        if (callback != null)
                        {
                            callback.onGetUgiException(e);
                        }
                        throw e;
                    }
                    try
                    {
                        return ugi.doAs(target);
                    } catch (Exception e)
                    {
                        throw new IllegalStateException(e);
                    }
                } finally
                {
                    lock.readLock().unlock();
                }
            }
        };
    }
}
