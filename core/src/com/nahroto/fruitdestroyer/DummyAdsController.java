package com.nahroto.fruitdestroyer;

public class DummyAdsController implements AdsController
{
    @Override
    public boolean isWifiConnected()
    {
        return false;
    }

    @Override
    public void showGoogleAd()
    {

    }

    @Override
    public void showAd()
    {

    }
}
