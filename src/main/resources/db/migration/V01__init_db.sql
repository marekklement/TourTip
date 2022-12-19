-- Create the Teams table
CREATE TABLE teams (
                       id uuid PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Tournaments table
CREATE TABLE tournaments (
                             id uuid PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL
);

-- Create the Users table
CREATE TABLE users (
                       id uuid PRIMARY KEY,
                       username VARCHAR(255) NOT NULL unique,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       roles VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Games table
CREATE TABLE games (
                       id uuid PRIMARY KEY,
                       tournament_id uuid NOT NULL REFERENCES tournaments(id),
                       home_team_id uuid NOT NULL REFERENCES teams(id),
                       away_team_id uuid NOT NULL REFERENCES teams(id),
                       start_time TIMESTAMP NOT NULL,
                       home_score INTEGER NOT NULL,
                       away_score INTEGER NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);

-- Create the Predictions table
CREATE TABLE predictions (
                             id uuid PRIMARY KEY,
                             user_id uuid NOT NULL REFERENCES users(id),
                             game_id uuid NOT NULL REFERENCES games(id),
                             home_score INTEGER NOT NULL,
                             away_score INTEGER NOT NULL,
                             points INTEGER NOT NULL DEFAULT 0,
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL
);

-- Create the Winners table
CREATE TABLE winners (
                         id uuid PRIMARY KEY,
                         user_id uuid NOT NULL REFERENCES users(id),
                         tournament_id uuid NOT NULL REFERENCES tournaments(id),
                         points INTEGER NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL
);
