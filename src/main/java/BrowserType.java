import java.util.HashMap;
import java.util.Map;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox");

    private String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    private String getName() {
        return browserName;
    }

    private static final Map<String, BrowserType> lookup = new HashMap<>();

   static
    {
        for(BrowserType browser : BrowserType.values())
        {
            lookup.put(browser.getName(), browser);
        }
    }

   public static BrowserType get(String browserName)
    {
        return lookup.get(browserName);
    }
}
