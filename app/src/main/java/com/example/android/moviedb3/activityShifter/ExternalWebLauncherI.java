package com.example.android.moviedb3.activityShifter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by nugroho on 28/07/17.
 */

public class ExternalWebLauncherI implements IActivityLauncher
{
    String webURL;
    Context context;

    public ExternalWebLauncherI(String webURL, Context context) {
        this.webURL = webURL;
        this.context = context;
    }

    @Override
    public void LaunchActivity()
    {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webURL));
        context.startActivity(webIntent);
    }
}
