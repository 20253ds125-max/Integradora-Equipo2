const bookings = JSON.parse(localStorage.getItem("gedsBookings") || "[]");
const fallbackVenue = JSON.parse(localStorage.getItem("gedsSelectedVenue") || "null") || {};
const latest = bookings[0] || {
  venue: fallbackVenue.name || "Hacienda Los Arcos",
  venueDetails: fallbackVenue,
  guests: Number(localStorage.getItem("gedsBookingGuests") || "25"),
  subtotal: Number(fallbackVenue.basePrice || 1200),
  serviceFee: 150,
  damageDeposit: Math.round((Number(fallbackVenue.basePrice || 1200) + 150) * 0.3),
  total: Number(fallbackVenue.basePrice || 1200) + 150 + Math.round((Number(fallbackVenue.basePrice || 1200) + 150) * 0.3),
  paidAt: new Date().toISOString()
};

function money(value) {
  return `$${Number(value || 0).toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
}

function write(selector, value) {
  const element = document.querySelector(selector);
  if (element) element.textContent = value;
}

const venueDetails = latest.venueDetails || fallbackVenue || {};
const ticketId = `EO-${new Date(latest.paidAt).getFullYear()}-${String(new Date(latest.paidAt).getTime()).slice(-6)}`;
const issued = new Date(latest.paidAt).toLocaleString("es-MX", { dateStyle: "medium", timeStyle: "short" });

write("[data-ticket-id]", ticketId);
write("[data-ticket-venue]", latest.venue || venueDetails.name || "Recinto seleccionado");
write("[data-ticket-location]", venueDetails.location || "Ubicación del recinto");
write("[data-ticket-date]", `Emitido: ${issued}`);
write("[data-ticket-guests]", `${latest.guests} `);
write("[data-ticket-subtotal]", money(latest.subtotal));
write("[data-ticket-service]", money(latest.serviceFee));
write("[data-ticket-deposit]", money(latest.damageDeposit));
write("[data-ticket-total]", money(latest.total));

const image = document.querySelector("[data-ticket-image]");
if (image && venueDetails.image) image.src = venueDetails.image;

const printButton = document.querySelector("[data-print-ticket]");
if (printButton) printButton.addEventListener("click", () => window.print());
