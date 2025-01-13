apiUrl="http://localhost:8080/Boulangerie";

// Fonction pour appeler l'API avec des arguments
async function appelerApi(url, method = 'GET', body = null, headers = {}) {
    try {
        // Ajouter les en-têtes par défaut si nécessaire
        const defaultHeaders = {
            'Content-Type': 'application/json',
        };
        
        const finalHeaders = { ...defaultHeaders, ...headers };

        // Options pour fetch
        const options = {
            method, // Méthode HTTP (GET, POST, etc.)
            headers: finalHeaders, // En-têtes
        };

        // Ajouter le corps si nécessaire (pour POST, PUT, etc.)
        if (body) {
            options.body = JSON.stringify(body);
        }

        // Effectuer l'appel avec fetch
        const response = await fetch(url, options);

        // Vérifiez si la réponse est réussie
        if (!response.ok) {
            throw new Error(`Erreur : ${response.status} - ${response.statusText}`);
        }

        // Parsez les données en JSON
        const data = await response.json();

        // Retourner les données
        return data;
    } catch (error) {
        // Gestion des erreurs
        console.error('Erreur lors de l\'appel API :', error);
    }
}

