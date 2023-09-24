

public class IronSourceConnexionClass {
    private static IronSourceConnexionClass instance;

    private IronSourceConnexionClass() {

        IronSource.init(this, appKey, IronSource.AD_UNIT.OFFERWALL, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);
    }

    public static IronSourceConnexionClass getInstance() {
        if (instance == null) {
            instance = new IronSourceConnexionClass();
        }
        return instance;
    }
}