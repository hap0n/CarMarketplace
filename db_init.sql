CREATE TABLE IF NOT EXISTS users (
                                     username VARCHAR PRIMARY KEY,
                                     pwd_hash VARCHAR
);

CREATE TABLE IF NOT EXISTS vehicles (
                                        id SERIAL PRIMARY KEY,
                                        make VARCHAR,
                                        model VARCHAR,
                                        year INT,
                                        mileage INT
);

CREATE TABLE IF NOT EXISTS posts (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR,
                                     vehicle_id INT,
                                     FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
                                    description VARCHAR
);
