package org.acra.util;

import org.apache.http.conn.scheme.SocketFactory;

import android.content.Context;

/**
 * Factory that creates an instance of a Https SocketFactory.
 */
public interface HttpsSocketFactoryFactory {

    /**
     * @param context   Android context for which to create the SocketFactory.
     * @return SocketFactory that was created.
     */
    public SocketFactory create(Context context);
}
