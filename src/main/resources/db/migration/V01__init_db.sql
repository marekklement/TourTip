-- Create the Teams table
CREATE TABLE teams (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Tournaments table
CREATE TABLE tournaments (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL
);

-- Create the Users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Games table
CREATE TABLE games (
                       id SERIAL PRIMARY KEY,
                       tournament_id INTEGER NOT NULL REFERENCES tournaments(id),
                       home_team_id INTEGER NOT NULL REFERENCES teams(id),
                       away_team_id INTEGER NOT NULL REFERENCES teams(id),
                       start_time TIMESTAMP NOT NULL,
                       home_score INTEGER NOT NULL,
                       away_score INTEGER NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Predictions table
CREATE TABLE predictions (
                             id SERIAL PRIMARY KEY,
                             user_id INTEGER NOT NULL REFERENCES users(id),
                             game_id INTEGER NOT NULL REFERENCES games(id),
                             home_score INTEGER NOT NULL,
                             away_score INTEGER NOT NULL,
                             points INTEGER NOT NULL DEFAULT 0,
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL
);

-- Create the Winners table
CREATE TABLE winners (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER NOT NULL REFERENCES users(id),
                         tournament_id INTEGER NOT NULL REFERENCES tournaments(id),
                         points INTEGER NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL
);
