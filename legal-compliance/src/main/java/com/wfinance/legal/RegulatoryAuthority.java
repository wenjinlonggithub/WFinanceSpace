package com.wfinance.legal;

/**
 * 监管机构
 * 全球主要金融市场的监管机构
 */
public enum RegulatoryAuthority {

    // 中国
    CSRC("中国证监会", "China Securities Regulatory Commission", "CN"),
    CBIRC("中国银保监会", "China Banking and Insurance Regulatory Commission", "CN"),
    PBOC("中国人民银行", "People's Bank of China", "CN"),

    // 美国
    SEC("美国证券交易委员会", "Securities and Exchange Commission", "US"),
    CFTC("美国商品期货交易委员会", "Commodity Futures Trading Commission", "US"),
    FINRA("美国金融业监管局", "Financial Industry Regulatory Authority", "US"),
    FED("美联储", "Federal Reserve", "US"),

    // 欧洲
    ESMA("欧洲证券和市场管理局", "European Securities and Markets Authority", "EU"),
    FCA("英国金融行为监管局", "Financial Conduct Authority", "UK"),
    BAFIN("德国联邦金融监管局", "Federal Financial Supervisory Authority", "DE"),

    // 亚洲其他
    FSA_JP("日本金融厅", "Financial Services Agency", "JP"),
    MAS("新加坡金融管理局", "Monetary Authority of Singapore", "SG"),
    SFC("香港证监会", "Securities and Futures Commission", "HK"),

    // 国际组织
    IOSCO("国际证监会组织", "International Organization of Securities Commissions", "INTL"),
    BIS("国际清算银行", "Bank for International Settlements", "INTL");

    private final String chineseName;
    private final String englishName;
    private final String countryCode;

    RegulatoryAuthority(String chineseName, String englishName, String countryCode) {
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.countryCode = countryCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
