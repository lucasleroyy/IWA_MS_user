CREATE TABLE users (
                       user_id serial NOT NULL PRIMARY KEY,
                        role varchar(250) NOT NULL ,
                       first_name varchar(30) NOT NULL,
                       last_name varchar(30) NOT NULL,
                       email varchar(80) NOT NULL,
                       phone_number varchar(20) NOT NULL,
                       password varchar(100) NOT NULL
);

-- Création de la table favorites
CREATE TABLE favoris (
                           favoris_id SERIAL PRIMARY KEY,
                           user_id INTEGER NOT NULL REFERENCES users(user_id),
                           location_id INTEGER NOT NULL
);

INSERT INTO users (first_name, role, last_name, email, phone_number, password)
VALUES ('Tom','user','Robinson', 'tom.rob@yopmail.com', '+15103754657', '123456');


-- Insertion d'exemples de favoris
INSERT INTO favoris (user_id, location_id) VALUES (1, 1);
