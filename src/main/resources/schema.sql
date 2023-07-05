DROP SCHEMA IF EXISTS 商品 CASCADE;
CREATE SCHEMA 商品;

CREATE TABLE 商品.商品
(
    商品ID UUID PRIMARY KEY,
    商品番号 SERIAL NOT NULL,
    商品名称 VARCHAR(40) NOT NULL,
    商品価格 int NOT NULL,
    画像パス VARCHAR(1024) NOT NULL -- S3のキー名は最大1024バイト(バイト数で制限できる型は無い？)
);

CREATE TABLE 商品.画像ファイルメタデータ
(
    商品ID UUID PRIMARY KEY,
    ファイル名 VARCHAR(1024) NOT NULL,
    ファイルサイズ int NOT NULL,
    最終アクセス日時 TIMESTAMP NOT NULL,
    最終更新日時 TIMESTAMP NOT NULL,
    作成日時 TIMESTAMP NOT NULL,
    所有者 VARCHAR(100) NOT NULL,
    ハッシュ値 VARCHAR(128) NOT NULL
);

