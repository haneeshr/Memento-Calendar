package com.alexstyl.specialdates.upcoming.widget.list;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.alexstyl.android.AndroidColorResources;
import com.alexstyl.android.AndroidDimensionResources;
import com.alexstyl.resources.DimensionResources;
import com.alexstyl.resources.StringResources;
import com.alexstyl.specialdates.android.AndroidStringResources;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.bankholidays.BankHolidayProvider;
import com.alexstyl.specialdates.events.bankholidays.BankHolidaysPreferences;
import com.alexstyl.specialdates.events.bankholidays.GreekBankHolidaysCalculator;
import com.alexstyl.specialdates.events.namedays.NamedayPreferences;
import com.alexstyl.specialdates.events.namedays.calendar.OrthodoxEasterCalculator;
import com.alexstyl.specialdates.events.namedays.calendar.resource.NamedayCalendarProvider;
import com.alexstyl.specialdates.images.UILImageLoader;
import com.alexstyl.specialdates.service.PeopleEventsProvider;
import com.alexstyl.specialdates.upcoming.BankHolidayViewModelFactory;
import com.alexstyl.specialdates.upcoming.ContactViewModelFactory;
import com.alexstyl.specialdates.upcoming.MonthLabels;
import com.alexstyl.specialdates.upcoming.NamedaysViewModelFactory;
import com.alexstyl.specialdates.upcoming.UpcomingDateStringCreator;
import com.alexstyl.specialdates.upcoming.UpcomingEventRowViewModelFactory;

import java.util.Locale;

public class UpcomingEventsRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        UpcomingEventsProvider peopleEventsProvider = createPeopleEventsProvider();
        DimensionResources dimensResources = new AndroidDimensionResources(getResources());
        CircularAvatarFactory avatarFactory = new CircularAvatarFactory(
                UILImageLoader.createLoader(getResources()),
                new AndroidColorResources(getResources())
        );
        return new UpcomingEventsViewsFactory(
                getPackageName(),
                peopleEventsProvider,
                dimensResources,
                this,
                avatarFactory
        );
    }

    private UpcomingEventsProvider createPeopleEventsProvider() {
        Date today = Date.today();
        StringResources stringResources = new AndroidStringResources(getResources());
        return new UpcomingEventsProvider(
                PeopleEventsProvider.newInstance(this),
                NamedayPreferences.newInstance(this),
                BankHolidaysPreferences.newInstance(this),
                new BankHolidayProvider(new GreekBankHolidaysCalculator(OrthodoxEasterCalculator.INSTANCE)),
                NamedayCalendarProvider.newInstance(getResources()),
                new UpcomingEventRowViewModelFactory(
                        today,
                        new UpcomingDateStringCreator(stringResources, today),
                        new ContactViewModelFactory(new AndroidColorResources(getResources()), stringResources),
                        stringResources,
                        new BankHolidayViewModelFactory(),
                        new NamedaysViewModelFactory(today),
                        MonthLabels.forLocale(Locale.getDefault())
                ),
                new NoAds()
        );
    }

}
