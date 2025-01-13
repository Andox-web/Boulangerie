<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-dashboard.css">
    <script src="<%=request.getContextPath()%>/assets/js/echarts.min.js"></script>
</head>
<body>
    <header>
        <h1>Tableau de Bord</h1>
    </header>
    <main>
        <section>
            <h2>Filtrer par Date</h2>
            <form id="dateFilterForm">
                <label for="dateDebut">Date Début :</label>
                <input type="date" id="dateDebut" name="dateDebut">
                <label for="dateFin">Date Fin :</label>
                <input type="date" id="dateFin" name="dateFin">
                <button type="submit">Filtrer</button>
            </form>
        </section>
        <section>
            <h2>Stock des Ingrédients et Produits</h2>
            <div style="display: flex; justify-content: space-between;">
                <div id="ingredientStockChart" style="width: 48%; height: 400px;"></div>
                <div id="productStockChart" style="width: 48%; height: 400px;"></div>
            </div>
        </section>
        <section>
            <h2>Ventes et Chiffre d'Affaires</h2>
            <div style="display: flex; justify-content: space-between;">
                <div id="salesChart" style="width: 48%; height: 400px;"></div>
                <div id="revenueChart" style="width: 48%; height: 400px;"></div>
            </div>
        </section>
        <section>
            <h2>Top Produits Vendus</h2>
            <ul id="topProductsList"></ul>
        </section>
    </main>
    <script>
        document.getElementById('dateFilterForm').addEventListener('submit', function(event) {
            event.preventDefault();
            fetchData();
        });

        function fetchData() {
            const dateDebut = document.getElementById('dateDebut').value;
            const dateFin = document.getElementById('dateFin').value;

            fetch(`<%=request.getContextPath()%>/dashboard/ingredient-quantities`)
                .then(response => response.json())
                .then(data => renderIngredientStockChart(data));
                // .catch(() => renderIngredientStockChart({
                //     labels: ["Farine", "Sucre", "Beurre"],
                //     data: [100, 50, 30]
                // }));

            fetch(`<%=request.getContextPath()%>/dashboard/product-quantities`)
                .then(response => response.json())
                .then(data => renderProductStockChart(data));
                // .catch(() => renderProductStockChart({
                //     labels: ["Pain", "Croissant", "Baguette"],
                //     data: [200, 150, 100]
                // }));

            fetch(`<%=request.getContextPath()%>/dashboard/sales?dateDebut=${dateDebut}&dateFin=${dateFin}`)
                .then(response => response.json())
                .then(data => renderSalesChart(data));
                // .catch(() => renderSalesChart({
                //     labels: ["2023-01-01", "2023-01-02", "2023-01-03"],
                //     products: {
                //         "Pain": [10, 20, 30],
                //         "Croissant": [5, 15, 25]
                //     }
                // }));

            fetch(`<%=request.getContextPath()%>/dashboard/total-sales?dateDebut=${dateDebut}&dateFin=${dateFin}`)
                .then(response => response.json())
                .then(data => renderRevenueChart(data));
                // .catch(() => renderRevenueChart({
                //     labels: ["2023-01-01", "2023-01-02", "2023-01-03"],
                //     products: {
                //         "Pain": [1000, 2000, 3000],
                //         "Croissant": [500, 1500, 2500]
                //     },
                //     totalRevenue: [1500, 3500, 5500]
                // }));

            fetch(`<%=request.getContextPath()%>/dashboard/most-sold-products`)
                .then(response => response.json())
                .then(data => renderTopProductsList(data))
                // .catch(() => renderTopProductsList([
                //     { nom: "Pain", quantiteVendue: 100 },
                //     { nom: "Croissant", quantiteVendue: 80 },
                //     { nom: "Baguette", quantiteVendue: 60 }
                // ]));
        }

        function renderIngredientStockChart(data) {
            const ingredientStockChart = echarts.init(document.getElementById('ingredientStockChart'));
            const colors = ['#ff3d00', '#ff9100', '#ffea00', '#00e676', '#2979ff'];
            const seriesData = Object.values(data).map((value, index) => ({
                value: value,
                itemStyle: { color: colors[index % colors.length] }
            }));
            ingredientStockChart.setOption({
                title: { text: 'Stock des Ingrédients', textStyle: { color: '#ffffff' } },
                tooltip: { textStyle: { color: '#121212' } },
                xAxis: { data: Object.keys(data), axisLine: { lineStyle: { color: '#ff5722' } }, axisLabel: { color: '#ff5722' } },
                yAxis: { axisLine: { lineStyle: { color: '#ff5722' } }, axisLabel: { color: '#ff5722' } },
                series: [{
                    name: 'Quantité',
                    type: 'bar',
                    data: seriesData
                }]
            });
        }

        function renderProductStockChart(data) {
            const productStockChart = echarts.init(document.getElementById('productStockChart'));
            const colors = ['#00e676', '#2979ff', '#ff4081', '#ffd740', '#69f0ae'];
            const seriesData = Object.values(data).map((value, index) => ({
                value: value,
                itemStyle: { color: colors[index % colors.length] }
            }));
            productStockChart.setOption({
                title: { text: 'Stock des Produits', textStyle: { color: '#ffffff' } },
                tooltip: { textStyle: { color: '#121212' } },
                xAxis: { data: Object.keys(data), axisLine: { lineStyle: { color: '#4caf50' } }, axisLabel: { color: '#4caf50' } },
                yAxis: { axisLine: { lineStyle: { color: '#4caf50' } }, axisLabel: { color: '#4caf50' } },
                series: [{
                    name: 'Quantité',
                    type: 'bar',
                    data: seriesData
                }]
            });
        }

        function renderSalesChart(data) {
            const salesChart = echarts.init(document.getElementById('salesChart'));
            const colors = [
                '#ff1744', '#00e5ff', '#d500f9', '#ffd740', 
                '#69f0ae', '#ff9100', '#2979ff', '#ff4081', 
                '#76ff03', '#ffea00'
            ];
            const labels = Object.keys(data);
            const products = {};
            labels.forEach(date => {
                Object.keys(data[date]).forEach(product => {
                    if (!products[product]) {
                        products[product] = [];
                    }
                    products[product].push(data[date][product][0]);
                });
            });
            const series = Object.keys(products).map((product, index) => ({
                name: product,
                type: 'line',
                data: products[product].slice(-100), // Limit to last 100 points
                itemStyle: { color: colors[index % colors.length] }
            }));
            salesChart.setOption({
                title: { text: 'Ventes', textStyle: { color: '#ffffff' } },
                tooltip: { trigger: 'axis', textStyle: { color: '#121212' } },
                legend: { data: Object.keys(products), textStyle: { color: '#ffffff' } },
                xAxis: { data: labels.slice(-100), axisLine: { lineStyle: { color: '#42a5f5' } }, axisLabel: { color: '#42a5f5' } },
                yAxis: { axisLine: { lineStyle: { color: '#42a5f5' } }, axisLabel: { color: '#42a5f5' } },
                series: series
            });
        }

        function renderRevenueChart(data) {
            const revenueChart = echarts.init(document.getElementById('revenueChart'));
            const colors = [
                '#ff4081', '#00b0ff', '#76ff03', '#ffea00', 
                '#ff3d00', '#651fff', '#ff1744', '#00e5ff', 
                '#d500f9', '#ffd740'
            ];
            const labels = Object.keys(data);
            const products = {};
            labels.forEach(date => {
                Object.keys(data[date]).forEach(product => {
                    if (!products[product]) {
                        products[product] = [];
                    }
                    products[product].push(data[date][product][0]);
                });
            });
            const series = Object.keys(products).map((product, index) => ({
                name: product,
                type: 'line',
                data: products[product].slice(-100), // Limit to last 100 points
                itemStyle: { color: colors[index % colors.length] }
            }));
            series.push({
                name: 'Total',
                type: 'line',
                data: data.totalRevenue.slice(-100), // Limit to last 100 points
                itemStyle: { color: '#ff8a65' }
            });
            revenueChart.setOption({
                title: { text: 'Chiffre d\'Affaires', textStyle: { color: '#ffffff' } },
                tooltip: { trigger: 'axis', textStyle: { color: '#121212' } },
                legend: { data: [...Object.keys(products), 'Total'], textStyle: { color: '#ffffff' } },
                xAxis: { data: labels.slice(-100), axisLine: { lineStyle: { color: '#ff4081' } }, axisLabel: { color: '#ff4081' } },
                yAxis: { axisLine: { lineStyle: { color: '#ff4081' } }, axisLabel: { color: '#ff4081' } },
                series: series
            });
        }

        function renderTopProductsList(data) {
            const topProductsList = document.getElementById('topProductsList');
            topProductsList.innerHTML = '';
            data.forEach(product => {
                const li = document.createElement('li');
                li.textContent = `${product[0]} - ${product[1]} unités vendues`;
                topProductsList.appendChild(li);
            });
        }

        fetchData();
    </script>
</body>
</html>
