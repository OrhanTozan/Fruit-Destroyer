package com.nahroto.fruitdestroyer;

public interface AdsController
{
    public boolean isWifiConnected();
    public void showInterstitialAd (Runnable then);
}
