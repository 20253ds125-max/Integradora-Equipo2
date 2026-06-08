const bookings = JSON.parse(localStorage.getItem("gedsBookings") || "[]");
const selectedVenue = JSON.parse(localStorage.getItem("gedsSelectedVenue") || "null") || {};
const latest = bookings[0] || {
  venue: selectedVenue.name || "Hacienda Los Arcos",
  guests: Number(localStorage.getItem("gedsBookingGuests") || "25"),
  subtotal: Number(selectedVenue.basePrice || 1200),
  serviceFee: 150,
  damageDeposit: Math.round((Number(selectedVenue.basePrice || 1200) + 150) * 0.3),
  total: Number(selectedVenue.basePrice || 1200) + 150 + Math.round((Number(selectedVenue.basePrice || 1200) + 150) * 0.3),
  paidAt: new Date().toISOString()
};

function money(value) {
  return `$${Number(value || 0).toLocaleString("en-US")}.00`;
}

function write(selector, value) {
  const element = document.querySelector(selector);
  if (element) element.textContent = value;
}

const ticketId = `GEDS-${new Date(latest.paidAt).getFullYear()}-${String(new Date(latest.paidAt).getTime()).slice(-6)}`;
const issued = new Date(latest.paidAt).toLocaleString("es-MX", { dateStyle: "medium", timeStyle: "short" });

write("[data-ticket-id]", ticketId);
write("[data-ticket-venue]", latest.venue || selectedVenue.name || "Recinto seleccionado");
write("[data-ticket-location]", selectedVenue.location || "Ubicacion del recinto");
write("[data-ticket-date]", `Emitido: ${issued}`);
write("[data-ticket-guests]", `${latest.guests} invitados`);
write("[data-ticket-subtotal]", money(latest.subtotal));
write("[data-ticket-service]", money(latest.serviceFee));
write("[data-ticket-deposit]", money(latest.damageDeposit));
write("[data-ticket-total]", money(latest.total));

const image = document.querySelector("[data-ticket-image]");
if (image && selectedVenue.image) image.src = selectedVenue.image;

document.querySelector("[data-print-ticket]").addEventListener("click", () => window.print());

