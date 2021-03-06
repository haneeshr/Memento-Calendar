package com.alexstyl.specialdates.contact;

import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

final class AndroidContactsQuery {

    public final static Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    static final String[] PROJECTION = {
            Contacts._ID, //0
            Contacts.LOOKUP_KEY,//1
            Contacts.DISPLAY_NAME_PRIMARY,//2
    };
    final static String SORT_ORDER = Contacts._ID;

    public static final int CONTACT_ID = 0;
    static final int LOOKUP_KEY = 1;
    static final int DISPLAY_NAME = 2;
}
