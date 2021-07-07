DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES
(
    BRAND_ID       INT,
    START_DATE     TIMESTAMP,
    END_DATE       TIMESTAMP,
    PRICE_LIST     INT,
    PRODUCT_ID     INT,
    PRIORITY       INT,
    PRICE          FLOAT,
    CURRENCY       VARCHAR,
    LAST_UPDATE    TIMESTAMP,
    LAST_UPDATE_BY VARCHAR
)
    AS
SELECT BrandId,
       convert(parseDateTime(StartDate, 'yyyy-MM-dd-hh.mm.ss'), timestamp),
       convert(parseDateTime(EndDate, 'yyyy-MM-dd-hh.mm.ss'), timestamp),
       PriceList,
       ProductId,
       Priority,
       Price,
       Currency,
       convert(parseDateTime(LastUpdate, 'yyyy-MM-dd-hh.mm.ss'), timestamp),
       LastUpdateBy
FROM CSVREAD('src/test/resources/prices.csv');