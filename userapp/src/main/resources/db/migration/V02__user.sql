CREATE TABLE IF NOT EXISTS STUFF_USER (
                                    ID TEXT NOT NULL,
                                    USERNAME TEXT NOT NULL,
                                    PASSWORD TEXT NOT NULL,
                                    document jsonb NOT NULL,
                                    PRIMARY KEY(ID)
    );
CREATE INDEX STUFF_USER_ID_IDX ON STUFF_USER (ID);
CREATE INDEX STUFF_USER_TITLE_IDX ON STUFF_USER (USERNAME);
