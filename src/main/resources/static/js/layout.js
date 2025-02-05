class LayoutApp {
    constructor() {
        this.categoryApi = new CategoryApi();
        this.productApi = new ProductApi();
        this.userApi = new UserApi();
        this.userId = this.getUserId();
        console.log("Fetched userId:", this.userId);
        this.searchForm = document.querySelector('form[role="search"]');
        this.searchInput = document.querySelector('input[type="search"]');
        this.ctgDropdown = document.getElementById('ctgDropdown');
        this.shoppingCartLink = document.getElementById('shoppingCartLink');
        this.wishlistLink = document.getElementById('wishlistLink');
        this.body = document.querySelector('body');
        this.init();
    }

    async init() {
        console.log("LayoutApp initialized");
        this.productApp = new ProductApp();
        await this.productApp.init();
        await this.loadCategories();
        this.initializeSearch();

    }

    async loadCategories() {
        console.log("Loading categories...");
        const categories = await this.categoryApi.findAll();
        this.renderCategories(categories);
    }

    renderCategories(categories) {
        console.log("Rendering categories...");
        this.ctgDropdown.innerHTML = '';

        categories.forEach(category => {
            const li = document.createElement('li');
            li.className = 'dropdown-item';
            const categoryLink = document.createElement('a');
            categoryLink.href = `home.html`;
            const p = document.createElement('p');
            p.textContent = category.name;
            categoryLink.appendChild(p);
            li.appendChild(categoryLink);
            this.ctgDropdown.appendChild(li);

            categoryLink.addEventListener('click', async (event) => {
                event.preventDefault();
                const products = await this.productApi.getProductsByCategory(category.name);
                this.productApp.renderProducts(products);
            });
        });
    }

    initializeSearch() {
        if (this.searchForm) {
            this.searchForm.addEventListener('submit', this.handleSearch.bind(this));
        } else {
            console.error('Search form not found');
        }
    }

    async handleSearch(event) {
        event.preventDefault();

        const searchTerm = this.searchInput.value.trim();
        if (!searchTerm || searchTerm.trim() === '') {
            await this.productApp.init()
            return;
        }

        try {
            const products = await this.productApi.getProductsByName(searchTerm);
            if (products.length === 0) {
                this.showSearchFeedback('No products found');
            }
            this.productApp.renderProducts(products);
        } catch (error) {
            console.error('Search failed:', error);
            this.showSearchFeedback('Search failed. Please try again.');
        }
    }

    showSearchFeedback(message) {
        const existingAlert = this.searchForm.querySelector('.alert');
        if (existingAlert) existingAlert.remove();

        const alert = document.createElement('div');
        alert.className = 'alert alert-info mt-2';
        alert.textContent = message;

        alert.style.position = 'absolute';
        alert.style.top = '100%';
        alert.style.left = '50%';
        alert.style.transform = 'translateX(-50%)';
        alert.style.padding = '5px 15px';
        alert.style.width = 'auto';
        alert.style.backgroundColor = '#f8d7da';
        alert.style.color = '#721c24';
        alert.style.border = '1px solid #f5c6cb';
        alert.style.borderRadius = '5px';
        alert.style.zIndex = '1000';
        this.searchForm.parentNode.insertBefore(alert, this.searchForm.nextSibling);

        setTimeout(() => alert.remove(), 3000);
    }

    getUserId() {
        const userIdElement = document.getElementById('userId');
        let userId
        if (userIdElement) {
            userId = userIdElement.textContent.trim() || 'guest'; // Ensure a valid value
            console.log("User ID found in layout:", userId);
        } else {
            userId = 'guest';
            console.log("User ID not found, setting to guest");
        }

        localStorage.setItem('userId', userId);
        return userId;
    }
}

addEventListener("DOMContentLoaded", () => {
    let layoutApp = new LayoutApp();
});