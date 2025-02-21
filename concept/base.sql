CREATE DATABASE boulangerie;

\c boulangerie;

CREATE TABLE Role (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

INSERT INTO Role (nom) VALUES ('admin'), ('client');

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL UNIQUE,
    motDePass VARCHAR(255) NOT NULL,
    idRole INT NOT NULL REFERENCES Role(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO utilisateur (nom, email, motDePass, idRole) VALUES ('admin', 'admin@admin.com','mdp',1);

CREATE TABLE typeCategorie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);
INSERT INTO typeCategorie(nom)  VALUES ('produit'),('ingrediant'); 
CREATE TABLE categorie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idTypeCategorie INT NOT NULL REFERENCES typeCategorie(id) ON DELETE CASCADE ON UPDATE CASCADE,
    description TEXT
);

CREATE TABLE produit (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    dateCreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ingrediant (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    unite VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE parfum (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE produitParfum (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idParfum INTEGER NOT NULL REFERENCES parfum(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(idProduit, idParfum)
);

CREATE TABLE recette (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    dureePreparation NUMERIC NOT NULL, -- en secondes
    description TEXT,
    dateCreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE detailRecette (
    id SERIAL PRIMARY KEY,
    idRecette INTEGER NOT NULL REFERENCES recette(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idIngrediant INTEGER NOT NULL REFERENCES ingrediant(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantite NUMERIC NOT NULL
);

CREATE TABLE produitCategorie (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idCategorie INTEGER NOT NULL REFERENCES categorie(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(idProduit, idCategorie)
);

CREATE TABLE ingrediantCategorie (
    id SERIAL PRIMARY KEY,
    idIngrediant INTEGER NOT NULL REFERENCES ingrediant(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idCategorie INTEGER NOT NULL REFERENCES categorie(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(idIngrediant, idCategorie)
);

CREATE TABLE categorieProduitRecete (
    id SERIAL PRIMARY KEY,
    idCategorieProduit INTEGER NOT NULL REFERENCES categorie(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE detailCategorieProduitRecette (
    id SERIAL PRIMARY KEY,
    idCategorieProduitRecete INTEGER NOT NULL REFERENCES categorieProduitRecete(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idCategorieIngrediant INTEGER NOT NULL REFERENCES categorie(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(idCategorieProduitRecete, idCategorieIngrediant)
);

CREATE TABLE stockProduit (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    entree NUMERIC,
    sortie NUMERIC,
    raison TEXT,
    dateTransaction TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stockIngrediant (
    id SERIAL PRIMARY KEY,
    idIngrediant INTEGER NOT NULL REFERENCES ingrediant(id) ON DELETE CASCADE ON UPDATE CASCADE,
    entree NUMERIC,
    sortie NUMERIC,
    raison TEXT,
    dateTransaction TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prixProduit (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    prix NUMERIC NOT NULL,
    datePrix TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE prixIngrediant (
    id SERIAL PRIMARY KEY,
    idIngrediant INTEGER NOT NULL REFERENCES ingrediant(id) ON DELETE CASCADE ON UPDATE CASCADE,
    prix NUMERIC NOT NULL,
    datePrix TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fabrication (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idRecette INTEGER NOT NULL REFERENCES recette(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantite NUMERIC NOT NULL,
    dateFabrication TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Sexe(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255)
);

CREATE TABLE vendeur(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255),
    numTel VARCHAR(255),
    idSexe INTEGER NOT NULL REFERENCES Sexe(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE commission (
    id SERIAL PRIMARY KEY,
    valeur NUMERIC,
    dateCommission TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE commissionVente (
    id SERIAL PRIMARY KEY,
    montant NUMERIC,
    idVendeur INTEGER REFERENCES vendeur(id) ON DELETE CASCADE ON UPDATE CASCADE,
    dateCommissionVente TIMESTAMP
);

CREATE TABLE vente (
    id SERIAL PRIMARY KEY,
    idUtilisateur INTEGER REFERENCES utilisateur(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idVendeur INTEGER REFERENCES vendeur(id) ON DELETE CASCADE ON UPDATE CASCADE,
    total NUMERIC NOT NULL,
    dateVente TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE detailVente (
    id SERIAL PRIMARY KEY,
    idVente INTEGER NOT NULL REFERENCES vente(id) ON DELETE CASCADE ON UPDATE CASCADE,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantite NUMERIC NOT NULL,
    total NUMERIC NOT NULL
);

CREATE TABLE achatIngrediant (
    id SERIAL PRIMARY KEY,
    idIngrediant INTEGER NOT NULL REFERENCES ingrediant(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantite NUMERIC NOT NULL,
    total NUMERIC NOT NULL
);

CREATE TABLE recommendation (
    id SERIAL PRIMARY KEY,
    idProduit INTEGER NOT NULL REFERENCES produit(id) ON DELETE CASCADE ON UPDATE CASCADE,
    dateDebut TIMESTAMP,
    dateFin TIMESTAMP
);

CREATE OR REPLACE VIEW v_produits_prix_recents AS
    SELECT 
        p.*,
        pp.prix AS prix,
        pp.datePrix AS produit_datePrix
    FROM 
        produit p
    JOIN 
        prixProduit pp
    ON 
        p.id = pp.idProduit
    WHERE 
        pp.datePrix = (
            SELECT MAX(pp2.datePrix)
            FROM prixProduit pp2
            WHERE pp2.idProduit = p.id
            AND pp2.datePrix <= NOW()
        );

--Inserer les commissions
INSERT INTO commission (valeur) VALUES (0.05);

--Inserer les sexe
INSERT INTO sexe (id,nom) VALUES (1,'homme'),(2,'femme');

--Inserer les vendeurs
INSERT INTO vendeur (nom, numTel, idSexe) VALUES ('Gerard', '034000000',1);
INSERT INTO vendeur (nom, numTel, idSexe) VALUES ('Louise', '033000000',2);
INSERT INTO vendeur (nom, numTel, idSexe) VALUES ('Claudette', '033000000',2);
INSERT INTO vendeur (nom, numTel, idSexe) VALUES ('Bob', '032000000',1)
        
-- Insérer des types de catégories
INSERT INTO typeCategorie (nom) VALUES ('produit'), ('ingrediant');

-- Insérer des catégories
INSERT INTO categorie (nom, idTypeCategorie, description)
VALUES 
('Pains', 1, 'Catégorie pour tous les types de pains.'),
('Viennoiseries', 1, 'Catégorie pour croissants, pains au chocolat, etc.'),
('Farines', 2, 'Catégorie pour différents types de farines.'),
('Sucrants', 2, 'Catégorie pour sucres et édulcorants.');

-- Insérer des produits
INSERT INTO produit (nom, description)
VALUES 
('Baguette Tradition', 'Une baguette artisanale avec une croûte croustillante.'),
('Croissant au Beurre', 'Un croissant feuilleté et riche en beurre.'),
('Pain au Chocolat', 'Viennoiserie feuilletée avec deux barres de chocolat.');

-- Insérer des ingrédients
INSERT INTO ingrediant (nom, unite, description)
VALUES 
('Farine de Blé T55', 'kg', 'Farine blanche classique pour pains et pâtisseries.'),
('Beurre Doux', 'kg', 'Beurre doux de qualité supérieure.'),
('Chocolat Noir 60%', 'kg', 'Chocolat noir pour pâtisseries.');

-- Insérer des parfums
INSERT INTO parfum (nom, description) VALUES 
('Vanille', 'Parfum doux et sucré de vanille.'),
('Chocolat', 'Parfum riche et intense de chocolat.'),
('Fraise', 'Parfum fruité et frais de fraise.');

-- Insérer des recettes
INSERT INTO recette (idProduit, dureePreparation, description)
VALUES 
(1, 3600, 'Recette de baguette avec fermentation lente.'),
(2, 2400, 'Recette classique pour croissant au beurre.'),
(3, 2400, 'Recette traditionnelle pour pain au chocolat.');

-- Insérer des détails de recettes
INSERT INTO detailRecette (idRecette, idIngrediant, quantite)
VALUES 
(1, 1, 1.0), -- 1 kg de farine pour baguette
(2, 1, 0.5), -- 0.5 kg de farine pour croissant
(2, 2, 0.3), -- 0.3 kg de beurre pour croissant
(3, 1, 0.5), -- 0.5 kg de farine pour pain au chocolat
(3, 3, 0.2); -- 0.2 kg de chocolat pour pain au chocolat

-- Insérer des catégories de produits
INSERT INTO produitCategorie (idProduit, idCategorie)
VALUES 
(1, 1), -- Baguette -> Pains
(2, 2), -- Croissant -> Viennoiseries
(3, 2); -- Pain au Chocolat -> Viennoiseries

-- Insérer des catégories d'ingrédients
INSERT INTO ingrediantCategorie (idIngrediant, idCategorie)
VALUES 
(1, 3), -- Farine de Blé -> Farines
(2, 4), -- Beurre Doux -> Sucrants
(3, 4); -- Chocolat Noir -> Sucrants

-- Insérer des transactions de stock pour produits
INSERT INTO stockProduit (idProduit, entree, sortie, raison)
VALUES 
(1, 200, 10, 'Production quotidienne'),
(2, 200, 5, 'Production quotidienne'),
(3, 200, 8, 'Production quotidienne');

-- Insérer des transactions de stock pour ingrédients
INSERT INTO stockIngrediant (idIngrediant, entree, sortie, raison)
VALUES 
(1, 500, 50, 'Commande fournisseur'),
(2, 200, 30, 'Commande fournisseur'),
(3, 100, 20, 'Commande fournisseur');

-- Insérer des prix pour produits
INSERT INTO prixProduit (idProduit, prix)
VALUES 
(1, 100000.0), -- Baguette à 1.20 €
(2, 200000.0), -- Croissant à 1.50 €
(3, 250000.0); -- Pain au Chocolat à 1.80 €

-- Insérer des prix pour ingrédients
INSERT INTO prixIngrediant (idIngrediant, prix)
VALUES 
(1, 0.80), -- Farine de Blé à 0.80 €/kg
(2, 5.00), -- Beurre à 5.00 €/kg
(3, 10.00); -- Chocolat à 10.00 €/kg
