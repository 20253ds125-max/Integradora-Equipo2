const bookingStorageKey = "gedsBookings";
const selectedVenueKey = "gedsSelectedVenue";
const selectedVenue = JSON.parse(localStorage.getItem(selectedVenueKey) || "null") || {
  name: "Hacienda Los Arcos",
  location: "San Miguel de Allende, Guanajuato",
  image: "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=500&q=85",
  basePrice: 1200,
  price: "$1,200"
};
const guests = Number(localStorage.getItem("gedsBookingGuests") || "25");
const subtotal = Number(selectedVenue.basePrice || String(selectedVenue.price || "$1,200").replace(/[^0-9]/g, "")) || 1200;
const serviceFee = 150;
const damageDeposit = Math.round((subtotal + serviceFee) * 0.3);
const total = subtotal + serviceFee + damageDeposit;

const summaryGuestLine = document.querySelector("[data-summary-guests]");
const depositAmount = document.querySelector("[data-deposit-amount]");
const totalAmount = document.querySelector("[data-total-amount]");
const payButton = document.querySelector("[data-pay-button]");
const statusText = document.querySelector("[data-payment-status]");
const paymentTabs = document.querySelectorAll(".payment-tabs button");
const paymentForm = document.querySelector("[data-payment-form]");
const summaryImage = document.querySelector(".summary-venue img");
const summaryTitle = document.querySelector(".summary-venue h2");
const summaryLocation = document.querySelector(".summary-venue p:first-of-type");
const rentalAmount = document.querySelector(".cost-list p:first-child strong");

function money(value) {
  return `$${value.toLocaleString("en-US")}.00`;
}

if (summaryImage && selectedVenue.image) summaryImage.src = selectedVenue.image;
if (summaryTitle) summaryTitle.textContent = selectedVenue.name;
if (summaryLocation) summaryLocation.textContent = selectedVenue.location;
if (summaryGuestLine) summaryGuestLine.textContent = `Oct 24, 2026 - ${guests} invitados`;
if (rentalAmount) rentalAmount.textContent = money(subtotal);
if (depositAmount) depositAmount.textContent = money(damageDeposit);
if (totalAmount) totalAmount.textContent = money(total);
if (payButton) payButton.textContent = `Confirmar y pagar ${money(total)}`;

paymentTabs.forEach((button) => {
  button.addEventListener("click", () => {
    paymentTabs.forEach((tab) => tab.classList.remove("active"));
    button.classList.add("active");
  });
});

if (paymentForm) paymentForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const bookings = JSON.parse(localStorage.getItem(bookingStorageKey) || "[]");
  bookings.unshift({ venue: selectedVenue.name, guests, subtotal, serviceFee, damageDeposit, total, paidAt: new Date().toISOString() });
  localStorage.setItem(bookingStorageKey, JSON.stringify(bookings));
  if (statusText) statusText.textContent = "Pago registrado localmente. Generando ticket de prueba.";
  setTimeout(() => { window.location.href = "ticket.html"; }, 900);
});



