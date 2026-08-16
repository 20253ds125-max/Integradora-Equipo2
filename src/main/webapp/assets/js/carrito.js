function money(value) {
    return `$${Number(value || 0).toLocaleString("en-US", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    })}`;
}

function renderCartPage() {
    const cart = window.GEDS_CART ? window.GEDS_CART.getCartSummary() : {
        recinto: null,
        servicios: [],
        subtotal: 0,
        serviceFee: 0,
        damageDeposit: 0,
        total: 0
    };

    const itemsContainer = document.querySelector("[data-cart-items]");
    const emptyState = document.querySelector("[data-cart-empty]");
    const subtotalNode = document.querySelector("[data-cart-subtotal]");
    const serviceFeeNode = document.querySelector("[data-cart-service-fee]");
    const depositNode = document.querySelector("[data-cart-deposit]");
    const totalNode = document.querySelector("[data-cart-total]");
    const venueNode = document.querySelector("[data-summary-venue]");
    const servicesNode = document.querySelector("[data-summary-services]");
    const subtotalSummaryNode = document.querySelector("[data-summary-subtotal]");
    const clearButton = document.querySelector("[data-clear-cart]");
    const checkoutLink = document.querySelector("[data-checkout]");

    const products = [];
    if (cart.recinto) {
        products.push({
            id: cart.recinto.id,
            type: "venue",
            name: cart.recinto.name,
            location: cart.recinto.location,
            image: cart.recinto.image,
            price: parseInt(String(cart.recinto.price).replace(/[^0-9]/g, ""), 10) || 0
        });
    }
    (cart.servicios || []).forEach((service) => {
        products.push({
            id: service.id,
            type: "service",
            name: service.name,
            location: service.localidadLabel || service.localidad || "Servicio extra",
            image: service.image || "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=600&q=80",
            price: Number(service.price || 0)
        });
    });

    if (!products.length) {
        emptyState.hidden = false;
        itemsContainer.innerHTML = "";
    } else {
        emptyState.hidden = true;
        itemsContainer.innerHTML = products
            .map((item) => `
                <div class="cart-row">
                    <div class="product-cell">
                        <img src="${item.image}" alt="${item.name}">
                        <div class="product-name">
                            <strong>${item.name}</strong>
                            <span>${item.location}</span>
                        </div>
                    </div>
                    <div class="date-cell">Pendiente</div>
                    <div class="price-cell">${money(item.price)}</div>
                    <div class="remove-cell">
                        <button class="remove-button" type="button" data-remove-item="${item.type}:${item.id}">&times;</button>
                    </div>
                </div>
            `)
            .join("");
    }

    subtotalNode.textContent = money(cart.subtotal);
    serviceFeeNode.textContent = money(cart.serviceFee);
    depositNode.textContent = money(cart.damageDeposit);
    totalNode.textContent = money(cart.total);
    subtotalSummaryNode.textContent = money(cart.subtotal);
    servicesNode.textContent = String((cart.servicios || []).length);
    venueNode.textContent = cart.recinto ? cart.recinto.name : "Sin recinto";

    if (checkoutLink) {
        checkoutLink.href = cart.total > 0 ? "pago.html" : "catalogo.jsp";
    }
}

document.addEventListener("click", (event) => {
    const clearButton = event.target.closest("[data-clear-cart]");
    if (clearButton && window.GEDS_CART) {
        window.GEDS_CART.clearCart();
        renderCartPage();
        return;
    }

    const removeButton = event.target.closest("[data-remove-item]");
    if (!removeButton || !window.GEDS_CART) return;

    const [kind, id] = removeButton.dataset.removeItem.split(":");
    if (kind === "venue") {
        window.GEDS_CART.removeCartVenue();
    } else {
        window.GEDS_CART.removeCartService(id);
    }
    renderCartPage();
});

document.addEventListener("DOMContentLoaded", renderCartPage);
